package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.history.RevisionRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}