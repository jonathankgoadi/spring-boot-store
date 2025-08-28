package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
import com.johneycodes.store.dtos.ErrorDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.entities.OrderItem;
import com.johneycodes.store.entities.OrderStatus;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.OrderRepository;
import com.johneycodes.store.services.AuthService;
import com.johneycodes.store.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequestDto request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null) {
            return ResponseEntity.badRequest().body(
                    new ErrorDto("cart doesn't exist")
            );
        }

        if(cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    new ErrorDto("Cart is empty")

            );
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));

    }
}
