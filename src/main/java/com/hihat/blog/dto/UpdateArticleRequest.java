package com.hihat.blog.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateArticleRequest {
    @NotBlank
    private String title;
    private String content;
    private String type;
    private java.util.List<Long> categoryIds;
}
