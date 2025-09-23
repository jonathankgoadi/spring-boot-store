package com.johneycodes.store.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull(message = "email is required")
    private String email;
    @NotNull(message = "password is required")
    private String password;
}
