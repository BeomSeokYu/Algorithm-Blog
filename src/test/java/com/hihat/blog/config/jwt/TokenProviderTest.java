package com.hihat.blog.config.jwt;

import com.hihat.blog.domain.User;
import com.hihat.blog.repository.RefreshTokenRepository;
import com.hihat.blog.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"test"})
class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private JwtProperties jwtProperties;

    @Test
    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰 제작")
    void generateToken() {
        // given
        User testUser = User.builder()
                .email("user@email.com")
                .password("test")
                .nickname("user")
                .build();
        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));
        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
    }

    @Test
    @DisplayName("validToken() : 만료된 토큰의 유효성 검증 실패 확인")
    void validToken() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);
        // when
        boolean result = tokenProvider.validToken(token);
        // then
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("getAuthentication() : 토큰 기반 인증 정보를 가져옴")
    void getAuthentication() {
        // given
        String userEmail = "user@email.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);
        // when
        Authentication authentication = tokenProvider.getAuthentication(token);
        // then
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername()).isEqualTo(userEmail);
    }

    @Test
    @DisplayName("getUserId() : 토큰으로 유저 ID를 가져옴")
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);
        // when
        Long userIdByToken = tokenProvider.getUserId(token);
        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}