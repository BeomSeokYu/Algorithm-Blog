package com.hihat.blog.repository;

import com.hihat.blog.domain.AlgorithmCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlgorithmCategoryRepository extends JpaRepository<AlgorithmCategory, Long> {
    List<AlgorithmCategory> findAllByOrderByNameKoAsc();
}
