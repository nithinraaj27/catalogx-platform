package com.catalogx.cartservice.service;

import com.catalogx.cartservice.dto.CartItemRequest;
import com.catalogx.cartservice.dto.CartResponse;

public interface CartService {

    CartResponse getCartByUser(String userId);

    CartResponse addItem(String userId, CartItemRequest request);

    CartResponse updateItem(String userId, CartItemRequest request);

    CartResponse removeItem(String userId, String sku);

    void clearCart(String userId);
}
