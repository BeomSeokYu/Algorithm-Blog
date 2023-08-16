package com.hihat.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserReauest {
    private String email;
    private String password;
}
