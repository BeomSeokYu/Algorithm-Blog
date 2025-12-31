package com.hihat.blog.dto;

import com.hihat.blog.domain.Article;
import lombok.Getter;

@Getter
public class ArticleResponse {
    private String title;
    private String content;
    private String type;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
        this.type = article.getType();
    }
}
