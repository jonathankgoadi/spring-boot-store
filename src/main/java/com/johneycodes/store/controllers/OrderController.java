package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.CheckoutRequestDto;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class OrderController {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;




    public ResponseEntity<Void> getAllOrder() {
        var orders = orderRepository;
        return null;
    }
}
