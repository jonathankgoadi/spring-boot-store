package com.johneycodes.store.services;

import com.johneycodes.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {
    private final ProductRepository productRepository;



    public  void createProduct(){
        productRepository.deleteById(3L);
    }
}
