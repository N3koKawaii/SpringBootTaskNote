package com.example.tasknote.tasknote.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tasknote.tasknote.exception.ResourceExistedException;
import com.example.tasknote.tasknote.exception.ResourceNotFoundException;
import com.example.tasknote.tasknote.model.AppUser;
import com.example.tasknote.tasknote.model_enum.Role;
import com.example.tasknote.tasknote.repository.UserRepository;
import com.example.tasknote.tasknote.requestDto.LoginRequest;
import com.example.tasknote.tasknote.requestDto.RegisterRequest;
import com.example.tasknote.tasknote.responseDto.UserResponseDTO;
import com.example.tasknote.tasknote.security.JwtUtil;
import com.example.tasknote.tasknote.security.TokenRevocationService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final TokenRevocationService tokenRevocationService;
    

    public UserService(
        UserRepository userRepository, 
        AuthenticationManager authenticationManager, 
        JwtUtil jwtUtil, 
        PasswordEncoder passwordEncoder,
        TokenRevocationService tokenRevocationService) {

        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
        this.tokenRevocationService = tokenRevocationService;
    }

    public String login(LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return jwtUtil.generateToken(userDetails);

        } catch (AuthenticationException ex) {
            throw new ResourceNotFoundException("Username or password invalid");
        }
    }

    public void logout(String token){
        String jti = jwtUtil.extractJti(token);
        String username = jwtUtil.extractUsername(token);
        tokenRevocationService.revokeToken(jti, username, 0);
    }

    public void logoutAll(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        tokenRevocationService.revokeAllTokensForUser(username);
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
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole(Role.USER);

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
