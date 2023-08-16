package com.hihat.blog.repository;

import com.hihat.blog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // 이메일로 유저 정보를 가져옴
}
