package com.johneycodes.store.mappers;


import com.johneycodes.store.dtos.OrderDto;
import com.johneycodes.store.entities.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(target = "items" , source = "orderItems")
    OrderDto toDto(Order order);
}
