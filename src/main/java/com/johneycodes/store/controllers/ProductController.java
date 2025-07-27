package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.ProductDto;
import com.johneycodes.store.dtos.RegisterProductRequest;
import com.johneycodes.store.entities.Product;
import com.johneycodes.store.mappers.ProductMapper;
import com.johneycodes.store.repositories.CategoryRepository;
import com.johneycodes.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("products")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

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

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto, UriComponentsBuilder uriBuilder) {
            var category = categoryRepository.findById(productDto.getCategoryId()).orElse(null);
            if (category == null) {
                return ResponseEntity.notFound().build();
            }
            var product = productMapper.toEntity(productDto);
            product.setCategory(category);

             productRepository.save(product);

            product.setId(product.getId());

            var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();


        return ResponseEntity.created(uri).body(productMapper.toDto(product));
    }
}
