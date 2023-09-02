package com.hihat.blog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup(@RequestParam(required = false) String email,
                         @RequestParam(required = false) String name,
                         Model model) {
        if (email != null && name != null) {
            model.addAttribute("email", email);
            model.addAttribute("name", name);
        }
        return "signup";
    }
}
