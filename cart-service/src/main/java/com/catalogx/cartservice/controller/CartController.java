package com.catalogx.cartservice.controller;

import com.catalogx.cartservice.dto.APIResponse;
import com.catalogx.cartservice.dto.CartItemRequest;
import com.catalogx.cartservice.dto.CartResponse;
import com.catalogx.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    @PostMapping("/{userId}")
    public ResponseEntity<APIResponse<CartResponse>> getOrCreate(@PathVariable String userId)
    {
        log.info("API: create or Fetch cart for user: {}", userId);
        return ResponseEntity.ok(APIResponse.success("Got the Cart item for the users", cartService.getCartByUser(userId)));
    }

    @PostMapping("/{userId}/items")
    public ResponseEntity<APIResponse<CartResponse>> addItem(@PathVariable String userId,@RequestBody CartItemRequest request){
        log.info("API: Add item {} x {} for user", request.sku(), request.quantity());
        return ResponseEntity.ok(APIResponse.success("Added the Item to the user's cart", cartService.addItem(userId, request)));
    }

    @DeleteMapping("/{userId}/items/{sku}")
    public ResponseEntity<APIResponse<CartResponse>> removeItem(@PathVariable String userId, @PathVariable String sku){
        log.info("API: Remove item {} for User {}", sku, userId);
        return ResponseEntity.ok(APIResponse.success("Removef the Item from the User's cart", cartService.removeItem(userId, sku)));
    }

    @PutMapping("{userId}/items")
    public ResponseEntity<APIResponse<CartResponse>> updateQuantity(@PathVariable String userId, @RequestBody CartItemRequest cartItemRequest)
    {
        log.info("API: Updates {} to qty {} for user {}", cartItemRequest.sku(), cartItemRequest.quantity(), userId);
        return ResponseEntity.ok(APIResponse.success("Updated the cart item ", cartService.updateItem(userId, cartItemRequest)));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<APIResponse<CartResponse>> getCart(@PathVariable String userId)
    {
        log.info("API: Get cart for user: {}", userId);
        return ResponseEntity.ok(APIResponse.success("Get the cart items for the particular user", cartService.getCartByUser(userId)));
    }
}
