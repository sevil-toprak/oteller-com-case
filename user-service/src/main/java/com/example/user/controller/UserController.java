package com.example.user.controller;

import com.example.user.model.request.LoginRequest;
import com.example.user.model.response.LoginResponse;
import com.example.user.security.JwtUtil2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final JwtUtil2 jwtUtil;

    public UserController(JwtUtil2 jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        // dummy authentication
        if ("user@test.com".equals(request.getEmail()) && "password".equals(request.getPassword())) {
            String token = jwtUtil.generateToken("1", "USER");
            LoginResponse response = new LoginResponse(token);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
