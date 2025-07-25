package com.johneycodes.store.mappers;

import com.johneycodes.store.dtos.RegisterUserRequest;
import com.johneycodes.store.dtos.UpdateUserRequest;
import com.johneycodes.store.dtos.UserDto;
import com.johneycodes.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toUserDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
