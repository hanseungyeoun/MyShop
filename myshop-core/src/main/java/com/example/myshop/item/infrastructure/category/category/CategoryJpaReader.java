package com.example.myshop.item.infrastructure.category.category;


import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryReader;
import com.example.myshop.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryJpaReader implements CategoryReader {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category getCategory(Long categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("category id not found %d", categoryId)));
   }

    @Override
    public List<Category> findByItemId(Long itemId) {
        return categoryJpaRepository.findByItemId(itemId);
    }

    @Override
    public List<Category> getCategoryParentPath(String parentPath) {
        return categoryJpaRepository.getCategoryParentPath(parentPath);
    }
}
