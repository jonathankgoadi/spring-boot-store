package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.AddItemToCartRequest;
import com.johneycodes.store.dtos.CartDto;
import com.johneycodes.store.dtos.CartItemDto;
import com.johneycodes.store.dtos.updateCartItemDto;
import com.johneycodes.store.entities.Cart;
import com.johneycodes.store.mappers.CartMapper;
import com.johneycodes.store.repositories.CartRepository;
import com.johneycodes.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;


    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){
        var cart = new Cart();
        cartRepository.save(cart);
        var cartDto = cartMapper.toDto(cart);

        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return  ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request, UriComponentsBuilder uriBuilder){

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.addCartItem(product);

        cartRepository.save(cart);

        var cartItemDto = cartMapper.toDto(cartItem);

        var uri =  uriBuilder.path("/carts/{cartId}").buildAndExpand(cartItem.getCart().getId()).toUri();

        return ResponseEntity.created(uri).body(cartItemDto);
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }
        var  cartDto = cartMapper.toDto(cart);

        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(@PathVariable UUID cartId,@PathVariable Long productId,@RequestBody updateCartItemDto request){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        var cartItem = cart.getCartItem(productId);

        if(cartItem == null){
            return ResponseEntity.notFound().build();
        }else{
            cartItem.setQuantity(request.getQuantity());
        }
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID cartId,@PathVariable Long productId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        cart.removeItem(productId);

       cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        cart.clearCart();

        cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }

}
