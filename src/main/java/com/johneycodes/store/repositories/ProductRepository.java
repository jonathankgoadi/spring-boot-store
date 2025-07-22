package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}