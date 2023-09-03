package com.hihat.blog.repository;

import com.hihat.blog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Article, Long> {
//    List<Article> findAllByType(String type);
    List<Article> findAllByAuthor(String author);

    Page<Article> findAllByType(String Type, Pageable pageable);
}
