package com.example.library.api.controller;

import com.example.library.api.request.LoginRequest;
import com.example.library.api.response.LoginResponse;
import com.example.library.auth.JwtUtil;
import com.example.library.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        final var authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.email(), loginRequest.password()));
        final var email = authentication.getName();
        final var user = User.builder()
                .email(email)
                .password("")
                .firstName("")
                .lastName("")
                .build();
        final var token = jwtUtil.createToken(user);
        final var loginRes = LoginResponse.builder()
                .email(email)
                .token(token)
                .build();
        return ResponseEntity.ok(loginRes);
    }
}