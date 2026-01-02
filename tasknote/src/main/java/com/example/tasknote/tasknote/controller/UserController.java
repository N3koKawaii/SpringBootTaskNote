package com.example.tasknote.tasknote.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.tasknote.tasknote.requestDto.RegisterRequest;
import com.example.tasknote.tasknote.responseDto.UserResponseDTO;
import com.example.tasknote.tasknote.service.UserService;
import com.example.tasknote.tasknote.util.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDTO>> Register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request){
        UserResponseDTO userResponseDTO = userService.register(registerRequest);

        String path = request.getRequestURI();

        return ResponseEntity.ok(ApiResponse.success(userResponseDTO, "User registered successfully", path));
    }
    

}
