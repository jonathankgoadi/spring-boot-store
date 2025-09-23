package com.johneycodes.store.services;

import com.johneycodes.store.dtos.OrderDto;
import com.johneycodes.store.entities.Order;
import com.johneycodes.store.users.User;
import com.johneycodes.store.exceptions.OrderNotFoundException;
import com.johneycodes.store.mappers.OrderMapper;
import com.johneycodes.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final AuthService authService;

    public List<OrderDto> getAllOrders(User customer){
        List<Order> orders = orderRepository.getOrdersByCustomer(customer);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId){

        var order = orderRepository.getOrderWithItems(orderId)
                                .orElseThrow(OrderNotFoundException::new);


        var user = authService.getCurrentUser();
        if(!order.isPlacedBy(user)){
            throw new AccessDeniedException("You don't have access to this order.");
        }

        return orderMapper.toDto(order);

    }
}
