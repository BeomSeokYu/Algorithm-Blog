package com.hihat.blog.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "algorithm_category")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AlgorithmCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name_ko", nullable = false)
    private String nameKo;

    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "source")
    private String source;

    @Column(name = "problem_count")
    private Integer problemCount;

    @Builder
    public AlgorithmCategory(String nameKo, String nameEn, String source, Integer problemCount) {
        this.nameKo = nameKo;
        this.nameEn = nameEn;
        this.source = source;
        this.problemCount = problemCount;
    }
}
