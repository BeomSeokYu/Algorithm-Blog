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
        int totalPages = pagingObj.getTotalPages();
        int currentPage = totalPages == 0 ? 1 : Math.min(Math.max(pageable.getPageNumber() + 1, 1), totalPages);
        int prevPage = Math.max(0, currentPage - 2);
        int nextPage = totalPages == 0 ? 0 : Math.min(totalPages - 1, currentPage);

        model.addAttribute("totalPages", totalPages);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("hasPrev", currentPage > 1);
        model.addAttribute("hasNext", currentPage < totalPages);
        model.addAttribute("prevPage", prevPage);
        model.addAttribute("nextPage", nextPage);
        model.addAttribute("lastPage", Math.max(0, totalPages - 1));
        model.addAttribute("paginationItems", buildPaginationItems(currentPage, totalPages));
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

    private List<Integer> buildPaginationItems(int currentPage, int totalPages) {
        if (totalPages <= 0) {
            return List.of();
        }
        if (totalPages <= 7) {
            return java.util.stream.IntStream.rangeClosed(1, totalPages).boxed().toList();
        }
        int start = Math.max(2, currentPage - 1);
        int end = Math.min(totalPages - 1, currentPage + 1);
        if (currentPage <= 3) {
            start = 2;
            end = 4;
        } else if (currentPage >= totalPages - 2) {
            start = totalPages - 3;
            end = totalPages - 1;
        }
        java.util.List<Integer> items = new java.util.ArrayList<>();
        items.add(1);
        if (start > 2) {
            items.add(-1);
        }
        for (int page = start; page <= end; page++) {
            items.add(page);
        }
        if (end < totalPages - 1) {
            items.add(-1);
        }
        items.add(totalPages);
        return items;
    }
}
