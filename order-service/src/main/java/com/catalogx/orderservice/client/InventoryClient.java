package com.catalogx.orderservice.client;

import com.catalogx.orderservice.dto.ReservationRequest;
import com.catalogx.orderservice.dto.ReservationResponse;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @PostMapping("/api/inventory/reserve")
    ReservationResponse reserve(@RequestBody ReservationRequest request);

    @PostMapping("/api/inventory/release")
    ReservationResponse release(@PathVariable String sku,@RequestParam int quantity);
}
