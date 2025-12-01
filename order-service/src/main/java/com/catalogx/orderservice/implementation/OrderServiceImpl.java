package com.catalogx.orderservice.implementation;

import com.catalogx.orderservice.client.InventoryClient;
import com.catalogx.orderservice.dto.OrderRequest;
import com.catalogx.orderservice.dto.OrderResponse;
import com.catalogx.orderservice.dto.ReservationRequest;
import com.catalogx.orderservice.dto.ReservationResponse;
import com.catalogx.orderservice.entity.InventoryProjection;
import com.catalogx.orderservice.entity.Order;
import com.catalogx.orderservice.entity.OrderStatus;
import com.catalogx.orderservice.events.OrderCreatedEvent;
import com.catalogx.orderservice.events.OrderEventsProducer;
import com.catalogx.orderservice.events.OrderStatusUpdatedEvent;
import com.catalogx.orderservice.exception.OrderNotFoundException;
import com.catalogx.orderservice.repository.InventoryProjectionRepository;
import com.catalogx.orderservice.repository.OrderRepository;
import com.catalogx.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventsProducer orderEventsProducer;

    private final InventoryClient inventoryClient;
    private final InventoryProjectionRepository projectionRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {

        log.info("Request Recieved for the Creating the Order");

        log.info("1. Check if inventory projection exists for SKU");
        InventoryProjection projection = projectionRepository.findById(request.sku())
                .orElseThrow(() -> new OrderNotFoundException("Inventory data not available for SKU"));

        log.info("2. Check local available quantity");
        if (projection.getAvailableQuantity() < request.quantity()) {
            Order rejectedOrder = Order.builder()
                    .sku(request.sku())
                    .quantity(request.quantity())
                    .status(OrderStatus.REJECTED)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderRepository.save(rejectedOrder);

            return rejectedOrder.toResponse();
        }

        log.info("3. Create Pre-ORder");
        Order preOrder = Order.builder()
                .sku(request.sku())
                .quantity(request.quantity())
                .status(OrderStatus.PENDING_RESERVATION)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.save(preOrder);

        log.info("Order id : {}",String.valueOf(preOrder.getOrderId()));
        log.info("4. Try reserving stock in inventory-service");
        ReservationRequest reservationRequest = new ReservationRequest(
                request.sku(),
                request.quantity(),
                String.valueOf(preOrder.getOrderId())
        );


        try {
            inventoryClient.reserve(reservationRequest);
        } catch (Exception ex) {

            log.error("Reservation failed for SKU {} with quantity {}. Reason: {}",
                    request.sku(),
                    request.quantity(),
                    ex.getMessage()
            );

            preOrder.setStatus(OrderStatus.REJECTED);
            preOrder.setUpdatedAt(LocalDateTime.now());
            orderRepository.save(preOrder);
            return preOrder.toResponse();
        }

        log.info("4. Reservation successful → Create PENDING order");
        Order order = Order.builder()
                .sku(request.sku())
                .quantity(request.quantity())
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        log.info("5. Publish OrderCreated Event");
        preOrder.setStatus(OrderStatus.PENDING);
        preOrder.setUpdatedAt(LocalDateTime.now());
        orderRepository.save(preOrder);

        OrderCreatedEvent event = new OrderCreatedEvent(
                preOrder.getOrderId(),
                preOrder.getSku(),
                preOrder.getQuantity(),
                preOrder.getStatus().name()
        );

        orderEventsProducer.publish("Order-Created", event);

        return order.toResponse();
    }

    @Override
    public OrderResponse getOrder(String orderId) {

        log.info("Request Recieved for fetching the Order");

        Long id = Long.parseLong(orderId);

        return orderRepository.findById(id)
                .map(Order::toResponse)
                .orElseThrow(() -> new OrderNotFoundException("Order Not Found"));
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        log.info("Request recieved for getting all the orders");
        return orderRepository.findAll()
                .stream()
                .map(Order::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void updateOrderService(String orderId, String status) {

        log.info("Request recieved for updating the order");
        Long id = Long.parseLong(orderId);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order Not found"));

        // 1. Release stock if needed
        if (status.equalsIgnoreCase("CANCELLED") || status.equalsIgnoreCase("FAILED")) {
            try {
                inventoryClient.release(order.getSku(), order.getQuantity());
            } catch (Exception ex) {
                log.error("Failed to release stock for SKU {}", order.getSku(), ex);
            }
        }

        // 2. Update order fields
        order.setStatus(OrderStatus.valueOf(status.toUpperCase()));
        order.setUpdatedAt(LocalDateTime.now());

        // 3. Save the order
        orderRepository.save(order);

        // 4. Publish event
        OrderStatusUpdatedEvent event = new OrderStatusUpdatedEvent(
                order.getOrderId(),
                order.getStatus().name(),
                "Status Updated"
        );

        orderEventsProducer.publish("Order-status-updated", event);

        log.info("Order {} updated to status {}", order.getOrderId(), status);
    }
}
