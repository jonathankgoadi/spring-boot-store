package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.ProductDto;
import com.johneycodes.store.entities.Product;
import com.johneycodes.store.mappers.ProductMapper;
import com.johneycodes.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public Iterable<ProductDto> getAllProducts(@RequestParam(name = "categoryId",required = false) Byte categoryId) {

        List<Product> products;
        if (categoryId != null) {
            products = productRepository.findByCategoryId(categoryId);
        }else {
            products = productRepository.findAllWithCategory();
        }

        return products.stream().map(productMapper::toDto).toList();

    }

    @GetMapping("{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable long id) {
        var product = productRepository.findById(id).orElse(null);
        if (product == null) {
                      return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productMapper.toDto(product));
    }
}
