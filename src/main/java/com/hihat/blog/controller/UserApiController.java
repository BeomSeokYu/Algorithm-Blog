package com.hihat.blog.controller;

import com.hihat.blog.config.oauth.OAuth2SuccessHandler;
import com.hihat.blog.dto.AddUserReauest;
import com.hihat.blog.service.UserService;
import com.hihat.blog.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public String signup(AddUserReauest reauest) {
        userService.save(reauest);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        CookieUtil.deleteCookie(request, response, OAuth2SuccessHandler.REFRESH_TOKEN_COOKIE_NAME);
        return "redirect:/login";
    }
}
