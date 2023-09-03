package com.hihat.blog.controller;

import com.hihat.blog.domain.Article;
import com.hihat.blog.dto.ArticleListViewResponse;
import com.hihat.blog.dto.ArticleViewResponse;
import com.hihat.blog.service.BlogService;
import com.hihat.blog.util.PageableImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping(value = {"/articles"})
    public String getArticles(@RequestParam(required = false) String type,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              Model model) {
        if (type == null) {
            type = "이론정리";
        }
        Pageable pageable = new PageableImpl(page, size);
        Page<Article> pagingObj = blogService.findAllByTypeAndPaging(type, pageable);
        List<ArticleListViewResponse> articles = pagingObj.getContent()
                .stream()
                .map(ArticleListViewResponse::new)
                .toList();
        model.addAttribute("articles", articles);
        model.addAttribute("totalPages", pagingObj.getTotalPages());
        model.addAttribute("pageable", pageable);
        model.addAttribute("type", type);
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            model.addAttribute("article", new ArticleViewResponse(blogService.findById(id)));
        }
        return "newArticle";
    }
}
