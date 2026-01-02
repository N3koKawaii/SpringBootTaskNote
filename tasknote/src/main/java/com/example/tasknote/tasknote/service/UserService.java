package com.example.tasknote.tasknote.service;

import org.springframework.stereotype.Service;

import com.example.tasknote.tasknote.exception.ResourceExistedException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model_enum.Role;
import com.example.tasknote.tasknote.repository.UserRepository;
import com.example.tasknote.tasknote.requestDto.LoginRequest;
import com.example.tasknote.tasknote.requestDto.RegisterRequest;
import com.example.tasknote.tasknote.responseDto.UserResponseDTO;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(LoginRequest loginRequest){
        
    }

    public UserResponseDTO register(RegisterRequest registerRequest){
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ResourceExistedException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ResourceExistedException("Email already exists");
        }

        AppUser user = new AppUser();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        user.setRole(Role.ROLE_USER);

        return toResponseDTO(userRepository.save(user));
    }

    // **************
    // DATA CONVERSION
    // **************

    // Convert Todo entity to UserResponseDTO
    private UserResponseDTO toResponseDTO(AppUser user){
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setEnabled(user.getEnabled());
        dto.setCreatedAt(user.getCreatedAt());
        return dto;
    }

}
