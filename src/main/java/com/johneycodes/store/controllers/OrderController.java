package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.OrderDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.mappers.OrderMapper;
import com.johneycodes.store.repositories.OrderRepository;
import com.johneycodes.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("orders")
public class OrderController {
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getAllOrder() {
        var user = authService.getCurrentUser();
        List<Order> orders = orderRepository.findByCustomer(user);
        orders.forEach(System.out::println);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    @GetMapping("{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable Long orderId) {
        var user = authService.getCurrentUser();

        var order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            return ResponseEntity.notFound().build();
        }

        if(!order.getCustomer().equals(user)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(orderMapper.toDto(order));
    }
}
