package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Cart;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

public interface CartRepository extends JpaRepositoryImplementation<Cart, UUID> {
}