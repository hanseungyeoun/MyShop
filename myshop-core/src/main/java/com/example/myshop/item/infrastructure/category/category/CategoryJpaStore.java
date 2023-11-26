package com.example.myshop.item.infrastructure.category.category;

import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryJpaStore implements CategoryStore {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category store(Category category) {
        return categoryJpaRepository.save(category);
    }

    @Override
    public void deleteWithChild(Long categoryId) {
        categoryJpaRepository.deleteWithChild();
    }
}
