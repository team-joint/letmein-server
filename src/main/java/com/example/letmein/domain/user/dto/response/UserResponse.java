package com.example.letmein.domain.user.dto.response;

import com.example.letmein.domain.user.domain.User;
import com.example.letmein.domain.user.domain.UserRole;

public record UserResponse(
        Long id,
        String username,
        UserRole role
) {
    public static UserResponse of(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getRole());
    }
}
