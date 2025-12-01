package com.catalogx.orderservice.service;

import com.catalogx.orderservice.dto.OrderRequest;
import com.catalogx.orderservice.dto.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    OrderResponse getOrder(String orderId);

    List<OrderResponse> getAllOrders();

    void updateOrderService(String orderId, String status);
}
