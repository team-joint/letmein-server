package com.example.letmein.domain.user.error;

import com.example.letmein.global.exception.CustomError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum UserError implements CustomError {
    USERNAME_DUPLICATION(HttpStatus.BAD_REQUEST, "username is already exist"),
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "user not found"),
    WRONG_PASSWORD(HttpStatus.BAD_REQUEST, "wrong password")
    ;

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return name();
    }
}
