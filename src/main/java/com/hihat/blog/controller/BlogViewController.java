package com.hihat.blog.controller;

import com.hihat.blog.domain.AlgorithmCategory;
import com.hihat.blog.domain.Article;
import com.hihat.blog.dto.AlgorithmCategoryOption;
import com.hihat.blog.dto.ArticleListViewResponse;
import com.hihat.blog.dto.ArticleViewResponse;
import com.hihat.blog.repository.AlgorithmCategoryRepository;
import com.hihat.blog.service.BlogService;
import com.hihat.blog.util.PageableImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BlogViewController {

    private final BlogService blogService;
    private final AlgorithmCategoryRepository categoryRepository;

    @GetMapping(value = {"/articles"})
    public String getArticles(@RequestParam(required = false) String type,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer size,
                              @RequestParam(required = false) String q,
                              @RequestParam(required = false) String sort,
                              @RequestParam(required = false) String preview,
                              @RequestParam(required = false) String categories,
                              Model model) {
        if (type == null) {
            type = "이론정리";
        }
        List<Long> categoryIds = parseCategoryIds(categories);
        String sortKey = normalizeSort(sort);
        Sort resolvedSort = resolveSort(sortKey);
        int resolvedSize = normalizeSize(size);
        Pageable pageable = new PageableImpl(page, resolvedSize, resolvedSort);
        Page<Article> pagingObj = blogService.search(type, q, categoryIds, pageable);
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
        model.addAttribute("query", q == null ? "" : q);
        model.addAttribute("sort", sortKey);
        model.addAttribute("preview", normalizePreview(preview));
        model.addAttribute("showPreview", !"off".equalsIgnoreCase(normalizePreview(preview)));
        model.addAttribute("pageSize", pageable.getPageSize());
        model.addAttribute("showFilters", true);
        model.addAttribute("selectedCategoryIds", categoryIds);
        model.addAttribute("selectedCategoryIdsCsv", joinCategoryIds(categoryIds));
        model.addAttribute("categoryOptions", categoryRepository.findAllByOrderByNameKoAsc()
                .stream()
                .map(AlgorithmCategoryOption::new)
                .toList());
        return "articleList";
    }

    @GetMapping("/articles/{id}")
    public String getArticle(@PathVariable Long id, Model model) {
        Article article = blogService.findById(id);
        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/new-article")
    public String newArticle(@RequestParam(required = false) Long id,
                             @RequestParam(required = false) String type,
                             @RequestParam(required = false) String categories,
                             Model model) {
        String defaultType = type != null ? type : "이론정리";
        List<Long> selectedCategoryIds = parseCategoryIds(categories);
        if (id == null) {
            model.addAttribute("article", new ArticleViewResponse());
        } else {
            Article article = blogService.findById(id);
            model.addAttribute("article", new ArticleViewResponse(article));
            defaultType = article.getType();
            selectedCategoryIds = article.getCategories().stream()
                    .map(AlgorithmCategory::getId)
                    .toList();
        }
        model.addAttribute("defaultType", defaultType);
        model.addAttribute("selectedCategoryIds", selectedCategoryIds);
        model.addAttribute("selectedCategoryIdsCsv", joinCategoryIds(selectedCategoryIds));
        model.addAttribute("contentPanelPadding", false);
        model.addAttribute("categoryOptions", categoryRepository.findAllByOrderByNameKoAsc()
                .stream()
                .map(AlgorithmCategoryOption::new)
                .toList());
        return "newArticle";
    }

    private List<Long> parseCategoryIds(String categories) {
        if (categories == null || categories.isBlank()) {
            return List.of();
        }
        String[] tokens = categories.split(",");
        List<Long> results = new ArrayList<>();
        for (String token : tokens) {
            String trimmed = token.trim();
            if (trimmed.isEmpty()) {
                continue;
            }
            try {
                results.add(Long.parseLong(trimmed));
            } catch (NumberFormatException ignored) {
                // Ignore invalid ids.
            }
        }
        return results.stream().distinct().collect(Collectors.toList());
    }

    private String normalizeSort(String sort) {
        if (sort == null || sort.isBlank()) {
            return "latest";
        }
        return switch (sort) {
            case "latest", "oldest", "title" -> sort;
            default -> "latest";
        };
    }

    private Sort resolveSort(String sortKey) {
        return switch (sortKey) {
            case "oldest" -> Sort.by("createdAt").ascending();
            case "title" -> Sort.by("title").ascending();
            default -> Sort.by("createdAt").descending();
        };
    }

    private String normalizePreview(String preview) {
        if (preview == null || preview.isBlank()) {
            return "on";
        }
        return "off".equalsIgnoreCase(preview) ? "off" : "on";
    }

    private int normalizeSize(Integer size) {
        if (size == null) {
            return 2;
        }
        return switch (size) {
            case 2, 4, 8 -> size;
            default -> 2;
        };
    }

    private String joinCategoryIds(List<Long> ids) {
        return ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
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
