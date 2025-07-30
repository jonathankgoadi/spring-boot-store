package com.johneycodes.store.mappers;

import com.johneycodes.store.dtos.CartDto;
import com.johneycodes.store.dtos.CartItemDto;
import com.johneycodes.store.entities.Cart;
import com.johneycodes.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    @Mapping(target = "items" , source = "cartItems")
    @Mapping(target = "totalPrice" , expression = "java(cart.getTotalPrice())" )
    CartDto toDto(Cart cart);

    @Mapping(target = "totalPrice" , expression = "java(cartItem.getTotalPrice())" )
    CartItemDto toDto(CartItem cartItem);
}
