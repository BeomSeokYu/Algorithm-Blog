package com.hihat.blog.controller;

import com.hihat.blog.domain.Article;
import com.hihat.blog.dto.AddArticleRequest;
import com.hihat.blog.dto.ArticleResponse;
import com.hihat.blog.dto.UpdateArticleRequest;
import com.hihat.blog.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/blog")
public class BlogApiController {

    private final BlogService blogService;

    @PostMapping("/articles")
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);    // 자원의 생성이 성공했다면 글 정보를 응답 객체에 담아 전송
    }

    @GetMapping("/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticle() {
        List<ArticleResponse> articles = blogService.findAll()
                .stream()
                .map(ArticleResponse::new)
                .toList();
        return ResponseEntity.ok().body(articles);
    }

    @GetMapping("/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);
        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    @DeleteMapping("/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);
        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id,
            @RequestBody UpdateArticleRequest request) {
        Article updatedArticle =blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
