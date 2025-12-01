package com.catalogx.orderservice.controller;

import com.catalogx.orderservice.dto.OrderRequest;
import com.catalogx.orderservice.dto.OrderResponse;
import com.catalogx.orderservice.entity.APIResponse;
import com.catalogx.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<APIResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request)
    {
        return ResponseEntity.ok(APIResponse.success("Order Created Successfully", orderService.createOrder(request)));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<APIResponse<OrderResponse>> getOrder(@PathVariable String orderId)
    {
        return ResponseEntity.ok(APIResponse.success("Order Fetched Successfully", orderService.getOrder(orderId)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<OrderResponse>>> getAll()
    {
        return ResponseEntity.ok(APIResponse.success("All Orders Fetched Successfully", orderService.getAllOrders()));
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<APIResponse<Void>> updateStatus(@PathVariable String orderId,@RequestParam String status){
        orderService.updateOrderService(orderId, status);
        return ResponseEntity.ok(APIResponse.success("Updated Successfully", null));
    }
}
