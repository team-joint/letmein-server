package com.example.letmein.global.exception;

import org.springframework.http.HttpStatus;

public interface CustomError {
    HttpStatus getStatus();
    String getMessage();
    String getCode();
}
