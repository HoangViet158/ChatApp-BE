package com.example.demo.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.AuthRequest;
import com.example.demo.dto.request.RefreshTokenRequest;
import com.example.demo.dto.request.UserRequest;
import com.example.demo.dto.response.ApiResponse;
import com.example.demo.dto.response.AuthResponse;
import com.example.demo.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    AuthService authService;
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
        @RequestBody AuthRequest request,
        HttpServletResponse response) {
        AuthResponse authResponse = authService.login(request.getEmail(), request.getPassword());
        Cookie cookie = new Cookie(
                "refresh_token",
                authResponse.getRefreshToken()
        );

        cookie.setHttpOnly(true);

        cookie.setSecure(true);

        cookie.setPath("/");

        cookie.setMaxAge(7 * 24 * 60 * 60);

        response.addCookie(cookie);

        return ApiResponse.<AuthResponse>
                builder()
                .code(200)
                .message("Login successful")
                .result(authResponse)
                .build();
        }

        @PostMapping("/register")
        public ApiResponse<AuthResponse> register(
            @Valid @RequestBody UserRequest request,
            HttpServletResponse response) {
            authService.register(request);
            return ApiResponse.<AuthResponse>
                    builder()
                    .code(200)
                    .message("Register successful")
                    .result(null)
                    .build();
        }
        @PostMapping("/refresh")
        public ApiResponse<AuthResponse> refreshToken(
         @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response) {
                AuthResponse authResponse = authService.refresh(refreshToken);
                Cookie cookie = new Cookie(
                        "refresh_token",
                        authResponse.getRefreshToken()
                );
                
                cookie.setHttpOnly(true);

                cookie.setSecure(true);

                cookie.setPath("/");

                cookie.setMaxAge(7 * 24 * 60 * 60);

                response.addCookie(cookie);
        return ApiResponse.<AuthResponse>
                builder()
                .code(200)
                .message("Login successful")
                .result(authResponse)
                .build();
        }

        @PostMapping("/logout")
        public ApiResponse<Void> logout(
            @RequestBody RefreshTokenRequest refreshToken,
            HttpServletResponse response) {             
                authService.logout(refreshToken.getToken());
                Cookie cookie = new Cookie(
                        "refresh_token",
                        null
                );
                
                cookie.setHttpOnly(true);

                cookie.setSecure(true);

                cookie.setPath("/");

                cookie.setMaxAge(0);

                response.addCookie(cookie);
        return ApiResponse.<Void>
                builder()
                .code(200)
                .message("Logout successful")
                .result(null)
                .build();
}
}
