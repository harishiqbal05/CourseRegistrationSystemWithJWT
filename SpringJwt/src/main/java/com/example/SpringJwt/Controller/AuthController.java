package com.example.SpringJwt.Controller;

import com.example.SpringJwt.Model.AuthenticationResponse;
import com.example.SpringJwt.Model.User;
import com.example.SpringJwt.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User request) {
      try {
        //  return ResponseEntity.ok("User registered successfully: " + authService.register(request));
          authService.register(request);
          return ResponseEntity.ok("User registered successfully");
      } catch (RuntimeException e) {
          return ResponseEntity.badRequest().body(e.getMessage());
      }

    }
@PostMapping("/login")
public ResponseEntity<AuthenticationResponse> login(@RequestBody User request) {
    return ResponseEntity.ok(authService.login(request));




}}
