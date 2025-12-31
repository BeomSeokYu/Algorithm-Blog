package com.hihat.blog.bootstrap;

import com.hihat.blog.domain.AlgorithmCategory;
import com.hihat.blog.repository.AlgorithmCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AlgorithmCategorySeeder {

    private final AlgorithmCategoryRepository categoryRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void seedAlgorithmCategories() {
        Set<String> existingNames = new HashSet<>();
        for (AlgorithmCategory category : categoryRepository.findAll()) {
            existingNames.add(category.getNameKo());
        }

        ClassPathResource resource = new ClassPathResource("seed/algorithm-categories.tsv");
        List<AlgorithmCategory> categories = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#") || trimmed.startsWith("분류")) {
                    continue;
                }

                String[] parts = trimmed.split("\\t", -1);
                if (parts.length < 2) {
                    continue;
                }

                String nameKo = parts[0].trim();
                String nameEn = parts[1].trim();
                String source = parts.length > 2 ? parts[2].trim() : "";
                String countValue = parts.length > 3 ? parts[3].trim() : "";

                Integer problemCount = null;
                if (!countValue.isBlank()) {
                    try {
                        problemCount = Integer.parseInt(countValue);
                    } catch (NumberFormatException ignored) {
                        problemCount = null;
                    }
                }

                if (!existingNames.contains(nameKo)) {
                    categories.add(AlgorithmCategory.builder()
                            .nameKo(nameKo)
                            .nameEn(nameEn)
                            .source(source.isBlank() ? null : source)
                            .problemCount(problemCount)
                            .build());
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load algorithm categories", e);
        }

        if (!categories.isEmpty()) {
            categoryRepository.saveAll(categories);
        }
    }
}
