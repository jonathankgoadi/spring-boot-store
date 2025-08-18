package com.johneycodes.store.controllers;

import com.johneycodes.store.dtos.JwtResponse;
import com.johneycodes.store.dtos.UserDto;
import com.johneycodes.store.dtos.UserLoginRequest;
import com.johneycodes.store.mappers.UserMapper;
import com.johneycodes.store.repositories.UserRepository;
import com.johneycodes.store.services.JwtService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    @PostMapping("login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody UserLoginRequest request){

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var token = jwtService.generateToken(request.getEmail());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/validate")
    public  boolean validate(@RequestHeader("Authorization") String authHeader){
        System.out.println("validate called");
        var token = authHeader.replace("Bearer ","");
        return jwtService.validateToken(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(){
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var email = (String) authentication.getPrincipal();

        var user = userRepository.findByEmail(email).orElse(null);
        if(user == null){
            return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(userMapper.toUserDto(user));

    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
