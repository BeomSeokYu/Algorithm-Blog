package com.hihat.blog.service;

import com.hihat.blog.domain.Article;
import com.hihat.blog.dto.AddArticleRequest;
import com.hihat.blog.dto.UpdateArticleRequest;
import com.hihat.blog.repository.BlogRepository;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    /**
     * 블로그에 글 추가 메서드
     * @param request : AddArticleRequest
     * @return : Article
     */
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    /**
     * 블로그 글 전체 목록 조회
     * @return : List<Article>
     */
    public List<Article> findAllByType(String type) {
        return blogRepository.findAllByType(type);
    }

    /**
     * 블로그 글 아이디로 조회
     * @param id : long id
     * @return : Article
     */
    public Article findById(long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));  // id가 없으면 예외 발생
    }

    /**
     * 블로그 글 아이디로 제거
     * @param id : long id
     */
    public void delete(long id) {
        Article article = blogRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authrizeArticleAuthor(article);
        blogRepository.deleteById(id);
    }

    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
        authrizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent(), request.getType());
        return article;
    }

    // 게시글을 작성한 유저인지 확인
    private static void authrizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
