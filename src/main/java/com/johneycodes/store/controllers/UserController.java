package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.UserDto;
import com.johneycodes.store.entities.User;
import com.johneycodes.store.mappers.UserMapper;
import com.johneycodes.store.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@RestController
@RequestMapping("users")
public class UserController {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping
    public Iterable<UserDto> getAllUsers(@RequestParam(required = false,defaultValue = "",name="sort") String sort) {

        if(!Set.of("name", "email").contains(sort))
            sort = "name";

       return userRepository.findAll(Sort.by(sort))
               .stream()
               .map(userMapper::toUserDto)
               .toList();
    }

    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(userMapper.toUserDto(user));
    }
}
