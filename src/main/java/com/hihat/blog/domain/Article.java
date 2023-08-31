package com.hihat.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Table(name = "article")
@Entity // 엔티티 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)  // protected 기본 생성자
@EntityListeners(AuditingEntityListener.class)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment
    @Column(name ="id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false) // Not Null
    private String title;

    @Column(name = "content", columnDefinition = "TEXT" ,nullable = false) // Not Null
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "type", nullable = false)
    private String type;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder    // 빌더 패턴으로 객체 생성
    public Article(String title, String content, String author, String type) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.type = type;
    }

    // 수정 메서드
    public void update(String title, String content, String type) {
        this.title = title;
        this.content = content;
        this.type = type;
    }
}
