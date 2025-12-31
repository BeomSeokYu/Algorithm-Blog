package com.hihat.blog.dto;

import com.hihat.blog.domain.AlgorithmCategory;
import com.hihat.blog.domain.Article;
import lombok.Getter;

import java.util.List;

@Getter
public class ArticleListViewResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final List<String> categories;

    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        if (article.getCategories() == null || article.getCategories().isEmpty()) {
            this.categories = List.of();
        } else {
            this.categories = article.getCategories().stream()
                    .map(AlgorithmCategory::getNameKo)
                    .toList();
        }
    }
}
