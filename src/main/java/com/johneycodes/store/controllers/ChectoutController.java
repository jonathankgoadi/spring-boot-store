package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.dtos.CheckoutResponse;
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
public class ChectoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequestDto request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null) {
            return ResponseEntity.badRequest().body(
                    Map.of("error","cart doesn't exist")
            );
        }

        if(cart.getCartItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("error","Cart is empty")
            );
        }

        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getCartItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getOrderItems().add(orderItem);
        });

        orderRepository.save(order);

        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));

    }
}
