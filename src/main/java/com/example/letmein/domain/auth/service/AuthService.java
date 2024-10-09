package com.example.letmein.domain.auth.service;


import com.example.letmein.domain.auth.dto.request.LoginRequest;
import com.example.letmein.domain.auth.dto.request.ReissueRequest;
import com.example.letmein.domain.auth.dto.request.SignUpRequest;
import com.example.letmein.domain.auth.repository.RefreshTokenRepository;
import com.example.letmein.domain.user.domain.User;
import com.example.letmein.domain.user.domain.UserRole;
import com.example.letmein.domain.user.dto.response.UserResponse;
import com.example.letmein.domain.user.error.UserError;
import com.example.letmein.domain.user.repository.UserRepository;
import com.example.letmein.global.exception.CustomException;
import com.example.letmein.global.security.jwt.dto.Jwt;
import com.example.letmein.global.security.jwt.enums.JwtType;
import com.example.letmein.global.security.jwt.error.JwtError;
import com.example.letmein.global.security.jwt.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignUpRequest request) {
        String username = request.username();
        String password = passwordEncoder.encode(request.password());

        if (userRepository.existsByUsername(username)) {
            throw new CustomException(UserError.USERNAME_DUPLICATION);
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .role(UserRole.LEADER)
                .build();

        userRepository.save(user);
    }

    @Transactional
    public Jwt login(LoginRequest request) {
        String username = request.username();
        String password = request.password();

        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(UserError.WRONG_PASSWORD);
        }

        Jwt token = jwtProvider.generateToken(username, user.getRole());

        refreshTokenRepository.save(username, token.refreshToken());

        return token;
    }

    @Transactional
    public Jwt reissue(ReissueRequest request) {
        String refreshToken = request.refreshToken();

        if (jwtProvider.getType(refreshToken) != JwtType.REFRESH)
            throw new CustomException(JwtError.INVALID_TOKEN_TYPE);

        String username = jwtProvider.getSubject(refreshToken);

        if (!refreshTokenRepository.existsByUserName(username))
            throw new CustomException(JwtError.INVALID_REFRESH_TOKEN);

        if (!refreshTokenRepository.findByUsername(username).equals(refreshToken))
            throw new CustomException(JwtError.INVALID_REFRESH_TOKEN);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        Jwt token = jwtProvider.generateToken(username, user.getRole());

        refreshTokenRepository.save(username, token.refreshToken());

        return token;
    }

    @Transactional
    public UserResponse me() {
        String username  = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        return UserResponse.of(user);
    }
}
