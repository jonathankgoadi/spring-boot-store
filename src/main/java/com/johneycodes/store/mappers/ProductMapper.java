package com.johneycodes.store.mappers;

import com.johneycodes.store.dtos.ProductDto;
import com.johneycodes.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);
}
