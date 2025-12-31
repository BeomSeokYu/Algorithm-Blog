package com.hihat.blog.controller;

import com.hihat.blog.dto.CreateAccessTokenRequest;
import com.hihat.blog.dto.CreateAccessTokenResponse;
import com.hihat.blog.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static com.hihat.blog.config.jwt.AuthTokenManager.REFRESH_TOKEN_COOKIE_NAME;

@RestController
@RequiredArgsConstructor
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping("/api/token")
    public ResponseEntity<CreateAccessTokenResponse> createAccessToken(
            @RequestBody(required = false) CreateAccessTokenRequest request,
            @CookieValue(value = REFRESH_TOKEN_COOKIE_NAME, required = false) String refreshTokenCookie) {
        String refreshToken = request != null ? request.getRefreshToken() : null;
        if (refreshToken == null || refreshToken.isBlank()) {
            refreshToken = refreshTokenCookie;
        }
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        try {
            String newAccessToken = tokenService.createNewAccessToken(refreshToken);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CreateAccessTokenResponse(newAccessToken));
        } catch (IllegalStateException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
