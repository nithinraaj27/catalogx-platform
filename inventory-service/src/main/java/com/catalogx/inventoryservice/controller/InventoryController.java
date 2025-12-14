package com.catalogx.inventoryservice.controller;

import com.catalogx.inventoryservice.dto.*;
import com.catalogx.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping
    public ResponseEntity<APIResponse<InventoryResponse>> create(@RequestBody InventoryRequest request)
    {
        return ResponseEntity.ok(APIResponse.success("Inventory Created Successfully", inventoryService.createInventory(request)));
    }

    @PostMapping("/update")
    public ResponseEntity<APIResponse<InventoryResponse>> update(@RequestBody InventoryRequest request)
    {
        return ResponseEntity.ok(APIResponse.success("Inventory Updated Successfully", inventoryService.updateInventory(request)));
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

    @PostMapping(value = "/reserve", consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<APIResponse<ReservationResponse>> reserve(@Valid @RequestBody ReservationRequest request){
        log.info("Reserve API called with request = {}", request);
        return ResponseEntity.ok(APIResponse.success("Stock Reserved Successfully",inventoryService.reserveStock(request)));
    }

    @PostMapping("/release/{sku}")
    public ResponseEntity<APIResponse<Void>> release(@PathVariable String sku, @Valid @RequestParam int quantity){
        inventoryService.releaseStock(sku, quantity);
        return  ResponseEntity.ok(APIResponse.success("Stock Released Successfully", null));
    }


}
