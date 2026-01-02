package com.example.userregistration.controller;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.userregistration.dto.ApiResponse;
import com.example.userregistration.dto.UserRegistrationRequest;
import com.example.userregistration.dto.UserResponseDto;
import com.example.userregistration.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> registerUser(
            @Valid @RequestBody UserRegistrationRequest request) {

        UserResponseDto userResponse = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        ApiResponse<UserResponseDto> response =
                new ApiResponse<>(201, "User registered successfully", userResponse);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
