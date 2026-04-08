package com.example.jwtportal.controller;

import com.example.jwtportal.entity.User;
import com.example.jwtportal.security.JwtUtil;
import com.example.jwtportal.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")   // IMPORTANT
public class AuthController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User dbUser = userService.findByUsername(user.getUsername());

        if (dbUser == null) {
            throw new RuntimeException("User not found");
        }

        if (dbUser.getPassword().equals(user.getPassword())) {
            return jwtUtil.generateToken(dbUser.getUsername(), dbUser.getRole());
        }

        throw new RuntimeException("Invalid credentials");
    }
}