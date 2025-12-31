package com.hihat.blog.dto;

import com.hihat.blog.domain.AlgorithmCategory;
import lombok.Getter;

@Getter
public class AlgorithmCategoryOption {
    private final Long id;
    private final String nameKo;
    private final String nameEn;
    private final String source;

    public AlgorithmCategoryOption(AlgorithmCategory category) {
        this.id = category.getId();
        this.nameKo = category.getNameKo();
        this.nameEn = category.getNameEn();
        this.source = category.getSource();
    }
}
