package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.RegisterUserRequest;
import com.johneycodes.store.dtos.UpdateUserRequest;
import com.johneycodes.store.dtos.UserDto;
import com.johneycodes.store.mappers.UserMapper;
import com.johneycodes.store.repositories.UserRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

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

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterUserRequest request, UriComponentsBuilder uriBuilder) {
        if(userRepository.existsByEmail(request.getEmail())){
            return ResponseEntity.badRequest().body(
                    Map.of("email","email already registered")
            );
        }
        var user = userMapper.toEntity(request);
        user = userRepository.save(user);

        var userDto = userMapper.toUserDto(user);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateUser(request, user);
         userRepository.save(user);

        return ResponseEntity.ok(userMapper.toUserDto(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        var user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.delete(user);

        return ResponseEntity.noContent().build();

    }

}
