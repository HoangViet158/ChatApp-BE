package com.example.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping()
    public ApiResponse<UserResponse> createUser(@Valid @RequestBody UserRequest request) {
        userService.CreateUser(request);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("User created successfully")
                .result(null)
                .build();
    }

    @PutMapping(value = "/{id}", consumes = org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<UserResponse> updateUser(
            @PathVariable("id") Long id,
            @Valid @RequestPart("request") UserRequest request,
            @RequestPart(value = "file", required = false) MultipartFile file) {
        UserResponse updatedUser = userService.updateUser(id, request, file);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("User updated successfully")
                .result(updatedUser)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable("id") Long id) {
        UserResponse userResponse = userService.getUserById(id);
        return ApiResponse.<UserResponse>builder()
                .code(200)
                .message("User retrieved successfully")
                .result(userResponse)
                .build();   
        }
    @GetMapping()
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .code(200)
                .message("Get all users successfully")
                .result(users)
                .build();
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteUser(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("User deleted successfully")
                .result(null)
                .build();
    }
}
