package com.example.jwtportal.service;

import com.example.jwtportal.entity.User;
import com.example.jwtportal.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User findByUsername(String username) {
        return repo.findByUsername(username);
    }
}