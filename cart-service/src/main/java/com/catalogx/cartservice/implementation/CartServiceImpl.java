package com.catalogx.cartservice.implementation;

import com.catalogx.cartservice.dto.CartItemRequest;
import com.catalogx.cartservice.dto.CartItemResponse;
import com.catalogx.cartservice.dto.CartResponse;
import com.catalogx.cartservice.entity.Cart;
import com.catalogx.cartservice.entity.CartItem;
import com.catalogx.cartservice.entity.InventoryProjection;
import com.catalogx.cartservice.exception.CartNotFoundException;
import com.catalogx.cartservice.exception.ItemNotFoundException;
import com.catalogx.cartservice.repository.CartItemRepository;
import com.catalogx.cartservice.repository.CartRepository;
import com.catalogx.cartservice.repository.InventoryProjectionRepository;
import com.catalogx.cartservice.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final InventoryProjectionRepository inventoryProjectionRepository;

    @Override
    public CartResponse getCartByUser(String userId) {

        log.info("Received request to get the cart for the user: {}",  userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newcart = Cart.builder().userId(userId).build();
                    return cartRepository.save(newcart);
                });

        return toResponse(cart);
    }

    @Override
    public CartResponse addItem(String userId, CartItemRequest request) {

        log.info("Request Recieved to add the item to the cart");

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(Cart.builder().userId(userId).build()));

        validateStock(request.sku(), request.quantity());

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getSku().equals(request.sku()))
                .findFirst()
                .orElse(null);

        if (item == null) {
            item = CartItem.builder()
                    .cart(cart)
                    .sku(request.sku())
                    .quantity(request.quantity())
                    .price(request.price())
                    .build();

            cart.getItems().add(item);
        } else {
            // newQty = existing + user wants to add
            int newQty = item.getQuantity() + request.quantity();

            validateStock(request.sku(), newQty);
            item.setQuantity(newQty);
        }

        cartItemRepository.save(item);
        return toResponse(cart);
    }

    @Override
    public CartResponse updateItem(String userId, CartItemRequest request) {

        log.info("Request Recieved to update cart item");
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not found for user: {}" + userId));

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getSku().equals(request.sku()))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item not found in cart: {} "+ request.sku()));

        validateStock(request.sku(), request.quantity());

        item.setQuantity(request.quantity());
        item.setPrice(request.price());

        cartItemRepository.save(item);

        return toResponse(cart);
    }

    @Override
    public CartResponse removeItem(String userId, String sku) {

        log.info("Request recieved to remove the item");

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart not found for user: "+ userId));

        CartItem item = cart.getItems()
                .stream()
                .filter(i -> i.getSku().equals(sku))
                .findFirst()
                .orElseThrow(() -> new CartNotFoundException("Item not found" + sku));

        cart.getItems().remove(item);
        cartItemRepository.delete(item);

        return toResponse(cartRepository.save(cart));
    }

    @Override
    public void clearCart(String userId) {
        log.info("Request recieved to clear the cart");

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new CartNotFoundException("Cart Not found for user: "+ userId));

        cartItemRepository.deleteAll(cart.getItems());
        cart.getItems().clear();

        cartRepository.save(cart);

    }

    private CartResponse toResponse(Cart cart)
    {

        return new CartResponse(
                cart.getId().toString(),
                cart.getUserId(),
                cart.getItems()
                        .stream()
                        .map(i -> new CartItemResponse(
                                i.getId().toString(),
                                i.getSku(),
                                i.getQuantity(),
                                i.getPrice()
                        )).collect(Collectors.toList()));
    }

    private void validateStock(String sku, int requestedQty) {
        InventoryProjection projection = inventoryProjectionRepository
                .findById(sku)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "No inventory information found for SKU: " + sku));

        int available = projection.getAvailableQuantity();
        if (requestedQty > available) {
            throw new IllegalArgumentException(
                    "Requested quantity " + requestedQty +
                            " exceeds available stock " + available +
                            " for SKU " + sku);
        }
    }

}
