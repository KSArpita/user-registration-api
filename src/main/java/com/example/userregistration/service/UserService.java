package com.example.userregistration.service;

import org.springframework.stereotype.Service;

import com.example.userregistration.dto.UserResponseDto;
import com.example.userregistration.entity.User;
import com.example.userregistration.exception.UserAlreadyExistsException;
import com.example.userregistration.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDto registerUser(String name, String email, String password) {

        // Business rule: email must be unique
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException(
                    "User with email " + email + " already exists"
            );
        }

        // Create and save entity
        User user = new User(name, email, password);
        User savedUser = userRepository.save(user);

        // Convert Entity → DTO
        return new UserResponseDto(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail()
        );
    }
}
