package com.hihat.blog.service;

import com.hihat.blog.domain.Article;
import com.hihat.blog.dto.AddArticleRequest;
import com.hihat.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BlogService {
    private final BlogRepository blogRepository;

    /**
     * 블로그에 글 추가 메서드
     * @param request : AddArticleRequest
     * @return : Article
     */
    public Article save(AddArticleRequest request) {
        return blogRepository.save(request.toEntity());
    }
}
