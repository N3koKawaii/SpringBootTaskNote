package com.example.tasknote.tasknote.requestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
    @NotNull
    @Size(min = 3, max = 50)
    private String username;

    @NotNull
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotNull
    @Email(message = "Invalid email format")
    private String email;

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
