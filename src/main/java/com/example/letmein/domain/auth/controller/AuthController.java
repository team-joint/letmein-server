package com.example.letmein.domain.auth.controller;


import com.example.letmein.domain.auth.dto.request.LoginRequest;
import com.example.letmein.domain.auth.dto.request.ReissueRequest;
import com.example.letmein.domain.auth.dto.request.SignUpRequest;
import com.example.letmein.domain.auth.service.AuthService;
import com.example.letmein.domain.user.dto.response.UserResponse;
import com.example.letmein.global.security.jwt.dto.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public void signup(@RequestBody SignUpRequest request) {
        authService.signup(request);
    }

    @PostMapping("/login")
    public Jwt login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/reissue")
    public Jwt Reissue(@RequestBody ReissueRequest request) {
        return authService.reissue(request);
    }

    @GetMapping("/me")
    public UserResponse me() {
        return authService.me();
    }
}
