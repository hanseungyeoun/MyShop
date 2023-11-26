package com.example.myshop.item.domain.category;

public interface CategoryStore {

    Category store(Category category);
    void deleteWithChild(Long categoryId);
}
