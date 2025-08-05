package com.example.SpringJwt.Service;

import com.example.SpringJwt.Exception.ResourceNotFoundException;
import com.example.SpringJwt.Filter.JwtAuthenticationFilter;
import com.example.SpringJwt.Model.AuthenticationResponse;
import com.example.SpringJwt.Model.Role;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
public class AuthService {

    UserRepository userRepository;
    JwtService jwtsService;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, JwtService jwtsService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtsService = jwtsService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {
        log.info("Attempting to register new user with username: {}", request.getUsername());
        
        try {
            // Check if username already exists
            if (userRepository.existsByUsername(request.getUsername())) {
                log.warn("Registration failed - username already exists: {}", request.getUsername());
                throw new RuntimeException("Username is already taken. Please choose a different username.");
            }
            
            // Create new user
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setRole(request.getRole());

            User savedUser = userRepository.save(user);
            log.info("User registered successfully with ID: {}", savedUser.getId());
            return new AuthenticationResponse("User registered successfully");
            
        } catch (Exception e) {
            log.error("Error during user registration: {}", e.getMessage(), e);
            throw e;
        }
    }

    public AuthenticationResponse login(User request) {
        log.info("Login attempt for user: {}", request.getUsername());
        
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            
            User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + request.getUsername()));
                
            String jwtToken = jwtsService.generateToken(user);
            log.info("User {} logged in successfully", request.getUsername());
            return new AuthenticationResponse(jwtToken);
            
        } catch (BadCredentialsException e) {
            log.warn("Login failed - invalid credentials for user: {}", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            log.error("Error during login for user {}: {}", request.getUsername(), e.getMessage(), e);
            throw e;
        }

    }



}
