package com.example.SpringJwt.Service;

import com.example.SpringJwt.Filter.JwtAuthenticationFilter;
import com.example.SpringJwt.Model.AuthenticationResponse;
import com.example.SpringJwt.Model.Role;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

@Service
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
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        String jwtToken = jwtsService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    public AuthenticationResponse login(User request) {
      authenticationManager.authenticate
              (new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        String jwtToken = jwtsService.generateToken(userRepository.findByUsername(request.getUsername()).get());
        return new AuthenticationResponse(jwtToken);

    }



}
