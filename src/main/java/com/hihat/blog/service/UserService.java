package com.hihat.blog.service;

import com.hihat.blog.domain.User;
import com.hihat.blog.dto.AddUserReauest;
import com.hihat.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Long save(AddUserReauest dto) {
        return userRepository.save(User.builder()
                .email(dto.getEmail())
                .password(getBCryptPasswordEncoder().encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .build()).getId();
    }

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("Unexpected user (Not Found)"));
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Unexpected user (Not Found)"));
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Unexpected user (Login Failed)"));
        if (!getBCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new IllegalStateException("password mismatch");
        }
        return user;
    }

    private BCryptPasswordEncoder getBCryptPasswordEncoder() {
        if (bCryptPasswordEncoder == null) {
            bCryptPasswordEncoder = new BCryptPasswordEncoder();
        }
        return bCryptPasswordEncoder;
    }
}
