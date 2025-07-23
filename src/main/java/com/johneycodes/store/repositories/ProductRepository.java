package com.johneycodes.store.repositories;

import com.johneycodes.store.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query(value = "select * from products p where p.price between  :min and :max",nativeQuery = true)
    List<Product> findProducts(@Param("min") BigDecimal min,@Param("max") BigDecimal max);
}