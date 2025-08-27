package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.OrderDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.mappers.OrderMapper;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.OrderRepository;
import com.johneycodes.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class OrderController {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    @GetMapping("orders")
    public List<OrderDto> getAllOrder() {
        var user = authService.getCurrentUser();
        List<Order> orders = orderRepository.findByCustomer(user);
        orders.forEach(System.out::println);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}
