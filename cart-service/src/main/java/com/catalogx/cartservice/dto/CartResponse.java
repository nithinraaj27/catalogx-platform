package com.catalogx.cartservice.dto;

import com.catalogx.cartservice.entity.Cart;
import com.catalogx.cartservice.entity.CartItem;

import java.util.List;

public record CartResponse(
        String cartId,
        String userId,
        List<CartItemResponse> items
) {}
