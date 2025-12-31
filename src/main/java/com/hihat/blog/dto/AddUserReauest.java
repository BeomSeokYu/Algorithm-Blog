package com.hihat.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddUserReauest {
    private String email;
    private String password;
    private String nickname;
}
