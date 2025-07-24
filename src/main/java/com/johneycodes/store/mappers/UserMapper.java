package com.johneycodes.store.mappers;

import com.johneycodes.store.dtos.UserDto;
import com.johneycodes.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
}
