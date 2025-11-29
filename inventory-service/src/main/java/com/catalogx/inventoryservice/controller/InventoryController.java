package com.catalogx.inventoryservice.controller;

import com.catalogx.inventoryservice.dto.*;
import com.catalogx.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<APIResponse<InventoryResponse>> createORUpdate(@RequestBody InventoryRequest request)
    {
        return ResponseEntity.ok(APIResponse.success("Inventory Updated Successfully", inventoryService.createOrUpdate(request)));
    }

    @GetMapping("/{sku}")
    public ResponseEntity<APIResponse<InventoryResponse>> getInventory(@PathVariable String sku)
    {
        return ResponseEntity.ok(APIResponse.success("Inventory Fetched Successfully", inventoryService.getInventory(sku)));
    }

    @GetMapping
    public ResponseEntity<APIResponse<List<InventoryResponse>>> getAllInventory()
    {
        return ResponseEntity.ok(APIResponse.success("All Inventory Fetched", inventoryService.getAllInventory()));
    }

    @PostMapping("/reserve")
    public ResponseEntity<APIResponse<ReservationResponse>> reserve(@Valid @RequestBody ReservationRequest request){
        return ResponseEntity.ok(APIResponse.success("Stock Reserved Successfully",inventoryService.reserveStock(request)));
    }

    @PostMapping("/release/{sku}")
    public ResponseEntity<APIResponse<Void>> release(@PathVariable String sku, @Valid @RequestParam int quantity){
        inventoryService.releaseStock(sku, quantity);
        return  ResponseEntity.ok(APIResponse.success("Stock Released Successfully", null));
    }


}
