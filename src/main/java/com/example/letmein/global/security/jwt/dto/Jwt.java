package com.example.letmein.global.security.jwt.dto;

public record Jwt(
        String accessToken,
        String refreshToken
) {
}
