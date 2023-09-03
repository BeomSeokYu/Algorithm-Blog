package com.hihat.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GetArticleRequest {
    private String type;
    private Integer page;
    private Integer size;
}
