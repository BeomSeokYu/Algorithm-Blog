package com.hihat.blog.dto;

import com.hihat.blog.domain.Article;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {
    @NotBlank
    private String title;
    private String content;
    private String type;
    private java.util.List<Long> categoryIds;

    public Article toEntity(String author) { // 생성자를 사용해 객체 생성
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .type(type)
                .build();
    }
}
