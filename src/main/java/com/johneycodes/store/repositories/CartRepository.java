package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Cart;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepositoryImplementation<Cart, UUID> {
    @EntityGraph(attributePaths = "cartItems.product")
    @Query("SELECT c FROM Cart c WHERE c.id = :cartId")
    Optional<Cart> getCartWithItems(@Param("cartId") UUID cartId);
}