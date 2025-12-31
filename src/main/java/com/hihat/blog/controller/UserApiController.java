package com.hihat.blog.controller;

import com.hihat.blog.config.jwt.AuthTokenManager;
import com.hihat.blog.domain.User;
import com.hihat.blog.dto.AddUserReauest;
import com.hihat.blog.dto.LoginUserReauest;
import com.hihat.blog.service.UserService;
import com.hihat.blog.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final AuthTokenManager authTokenManager;

    @PostMapping("/login")
    public void Login(@ModelAttribute LoginUserReauest loginInfo, HttpServletRequest request , HttpServletResponse response) throws IOException {
        try {
            User user = userService.login(loginInfo.getUsername(), loginInfo.getPassword());
            String targetUrl = authTokenManager.progressAuthenticationTokenIssuance(request, response, user);
            response.sendRedirect(targetUrl);
        } catch (IllegalStateException ex) {
            response.sendRedirect("/login?error");
        }
    }

    @PostMapping("/user")
    public String signup(@ModelAttribute AddUserReauest reauest) {
        userService.save(reauest);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(response, AuthTokenManager.REFRESH_TOKEN_COOKIE_NAME, true, request.isSecure(), "Lax");
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}
