package com.hihat.blog.config.jwt;

import com.hihat.blog.domain.RefreshToken;
import com.hihat.blog.domain.User;
import com.hihat.blog.repository.RefreshTokenRepository;
import com.hihat.blog.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.Duration;

@Component
@RequiredArgsConstructor
public class AuthTokenManager {
    public static final String REFRESH_TOKEN_COOKIE_NAME = "refresh_token";
    public static final Duration REFRESH_TOKEN_DURATION = Duration.ofDays(14);
    public static final String REDIRECT_PATH = "/";

    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param authenticatedUser User
     * @return return TargetUrl
     */
    public String progressAuthenticationTokenIssuance(HttpServletRequest request, HttpServletResponse response, User authenticatedUser) {
        // 리프레시 토큰 생성 및 저장, 쿠키에 저장
        String refreshToken = tokenProvider.generateToken(authenticatedUser, REFRESH_TOKEN_DURATION);
        saveRefreshToken(authenticatedUser.getId(), refreshToken);
        addRefreshTokenToCookie(request, response, refreshToken);

        return getTargetUrl();
    }

    // 생성된 리프레시 토큰을 전달받아 데이터베이스에 저장
    private void saveRefreshToken(Long userId, String newRefreshToken) {
        RefreshToken refreshToken = refreshTokenRepository.findByUserId(userId)
                .map(entity -> entity.update(newRefreshToken))
                .orElse(new RefreshToken(userId, newRefreshToken));
        refreshTokenRepository.save(refreshToken);
    }

    // 생성된 리프레시 토큰을 쿠키에 저장
    private void addRefreshTokenToCookie(HttpServletRequest request, HttpServletResponse response, String refreshToken) {
        int cookieMaxAge = (int) REFRESH_TOKEN_DURATION.toSeconds();
        CookieUtil.deleteCookie(response, REFRESH_TOKEN_COOKIE_NAME, true, request.isSecure(), "Lax");
        CookieUtil.addCookie(response, REFRESH_TOKEN_COOKIE_NAME, refreshToken, cookieMaxAge, true, request.isSecure(), "Lax");
    }

    // 액세스 토큰을 패스에 추가
    private String getTargetUrl() {
        return UriComponentsBuilder.fromUriString(REDIRECT_PATH)
                .build()
                .toUriString();
    }
}
