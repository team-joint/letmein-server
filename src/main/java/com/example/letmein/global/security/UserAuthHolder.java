package com.example.letmein.global.security;


import com.example.letmein.domain.user.domain.User;
import com.example.letmein.domain.user.error.UserError;
import com.example.letmein.domain.user.repository.UserRepository;
import com.example.letmein.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserAuthHolder {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));
    }
}
