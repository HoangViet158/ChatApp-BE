package com.example.demo.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.User;
import com.example.demo.exception.AppException;
import com.example.demo.repository.RefreshTokenRepository;
import com.example.demo.enums.ErrorCode;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private static final long REFRESH_TOKEN_EXPIRED = 60 * 60 * 24 * 7;; // 7 days

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(User user) {

        String token = generateSecureToken();

        LocalDateTime expiredAt = LocalDateTime.now().plusSeconds(REFRESH_TOKEN_EXPIRED);

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiredAt(expiredAt)
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    private String generateSecureToken() {

        SecureRandom secureRandom = new SecureRandom();

        byte[] bytes = new byte[64];

        secureRandom.nextBytes(bytes);

        return Base64.getUrlEncoder()
                .withoutPadding()
                .encodeToString(bytes);
    }

    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new AppException(ErrorCode.INVALID_REFRESH_TOKEN));

        if (refreshToken.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.REFRESH_TOKEN_EXPIRED);
        }

        return refreshToken;
    }

    public void revokeToken(String token) {

        RefreshToken refreshToken =
                refreshTokenRepository.findByToken(token)
                        .orElseThrow(() ->
                                new AppException(ErrorCode.INVALID_REFRESH_TOKEN));
        refreshTokenRepository.delete(refreshToken);
    }
}
