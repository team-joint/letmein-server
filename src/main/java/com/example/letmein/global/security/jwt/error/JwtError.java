package com.example.letmein.global.security.jwt.error;


import com.example.letmein.global.exception.CustomError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum JwtError implements CustomError {
    EXPIRED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "Expired JWT token"),
    INVALID_JWT_TOKEN(HttpStatus.BAD_REQUEST, "Invalid JWT token"),
    UNSUPPORTED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "Unsupported JWT token"),
    MALFORMED_JWT_TOKEN(HttpStatus.BAD_REQUEST, "Malformed JWT token"),
    INVALID_TOKEN_TYPE(HttpStatus.BAD_REQUEST, "Invalid token type"),
    INVALID_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "Invalid refresh token")
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
