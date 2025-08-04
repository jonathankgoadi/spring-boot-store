package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.AddItemToCartRequest;
import com.johneycodes.store.dtos.CartDto;
import com.johneycodes.store.dtos.CartItemDto;
import com.johneycodes.store.dtos.updateCartItemDto;
import com.johneycodes.store.exceptions.CartNotFoundException;
import com.johneycodes.store.exceptions.ProductNotFoundException;
import com.johneycodes.store.services.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private CartService cartService;


    @PostMapping
    public ResponseEntity<CartDto> createCart(UriComponentsBuilder uriBuilder){

        var cartDto = cartService.createCart();
        var uri = uriBuilder.path("/carts/{id}").buildAndExpand(cartDto.getId()).toUri();

        return  ResponseEntity.created(uri).body(cartDto);
    }

    @PostMapping("{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(@PathVariable UUID cartId, @RequestBody AddItemToCartRequest request, UriComponentsBuilder uriBuilder){

        var cartItemDto = cartService.addToCart(cartId, request.getProductId());

        var uri =  uriBuilder.path("/carts/{cartId}").buildAndExpand(cartId).toUri();

        return ResponseEntity.created(uri).body(cartItemDto);
    }

    @GetMapping("{cartId}")
    public ResponseEntity<CartDto> getCart(@PathVariable UUID cartId){

        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }

    @PutMapping("{cartId}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(@PathVariable UUID cartId,@PathVariable Long productId,@RequestBody updateCartItemDto request){
        var cartItem = cartService.updateCart(cartId,productId,request.getQuantity());

        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("{cartId}/items/{productId}")
    public ResponseEntity<Void> removeItem(@PathVariable UUID cartId,@PathVariable Long productId){

        cartService.removeItem(cartId,productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{cartId}/items")
    public ResponseEntity<Void> clearCart(@PathVariable UUID cartId){
        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Product not found in the cart."));
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", "Cart not found."));
    }

}
