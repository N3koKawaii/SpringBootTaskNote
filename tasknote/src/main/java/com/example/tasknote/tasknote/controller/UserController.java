package com.example.tasknote.tasknote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tasknote.tasknote.requestDto.LoginRequest;
import com.example.tasknote.tasknote.requestDto.RegisterRequest;
import com.example.tasknote.tasknote.responseDto.UserResponseDTO;
import com.example.tasknote.tasknote.service.UserService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginRequest loginRequest){ 
        String token = userService.login(loginRequest);
        return ResponseEntity.ok(ApiResponse.success(token, "Login successful"));
    }
    
    

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@Valid @RequestBody RegisterRequest registerRequest){
        UserResponseDTO userResponseDTO = userService.register(registerRequest);

        return ResponseEntity.ok(ApiResponse.success(userResponseDTO, "User registered successfully"));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpServletRequest request){ 
        
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing Authorization header");
        }

        userService.logout(header.substring(7)); 
        return ResponseEntity.ok(ApiResponse.success(null, "Logout successful"));
    }

    @PostMapping("/logout-all")
    public ResponseEntity<ApiResponse<Void>> logoutAll() {

        userService.logoutAll();

        return ResponseEntity.ok(
            ApiResponse.success(null, "Logged out from all devices")
        );
    }
        
    
    

}
