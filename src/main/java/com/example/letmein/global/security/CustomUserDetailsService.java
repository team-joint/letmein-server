package com.example.letmein.global.security;


import com.example.letmein.domain.user.domain.User;
import com.example.letmein.domain.user.error.UserError;
import com.example.letmein.domain.user.repository.UserRepository;
import com.example.letmein.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new CustomException(UserError.USER_NOT_FOUND));

        return new CustomUserDetails(user);
    }
}
