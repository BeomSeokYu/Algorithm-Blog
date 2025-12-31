package com.hihat.blog.repository;

import com.hihat.blog.domain.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BlogRepository extends JpaRepository<Article, Long> {
//    List<Article> findAllByType(String type);
    List<Article> findAllByAuthor(String author);

    Page<Article> findAllByType(String Type, Pageable pageable);

    @Query(
            value = """
                    SELECT DISTINCT a
                    FROM Article a
                    LEFT JOIN a.categories c
                    WHERE (:type IS NULL OR a.type = :type)
                      AND (:keyword IS NULL OR :keyword = '' OR a.title LIKE %:keyword% OR a.content LIKE %:keyword%)
                      AND (:categoryIds IS NULL OR c.id IN :categoryIds)
                    """,
            countQuery = """
                    SELECT COUNT(DISTINCT a.id)
                    FROM Article a
                    LEFT JOIN a.categories c
                    WHERE (:type IS NULL OR a.type = :type)
                      AND (:keyword IS NULL OR :keyword = '' OR a.title LIKE %:keyword% OR a.content LIKE %:keyword%)
                      AND (:categoryIds IS NULL OR c.id IN :categoryIds)
                    """
    )
    Page<Article> search(
            @Param("type") String type,
            @Param("keyword") String keyword,
            @Param("categoryIds") List<Long> categoryIds,
            Pageable pageable);
}
