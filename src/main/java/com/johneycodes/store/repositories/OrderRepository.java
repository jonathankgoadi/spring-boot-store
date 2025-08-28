package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Order;
import com.johneycodes.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(User user);
}