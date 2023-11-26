package com.example.myshop.item.domain.category;

import java.util.List;

public interface CategoryReader {

    Category getCategory(Long categoryId);

    List<Category> findByItemId(Long itemId);

    List<Category> getCategoryParentPath(String parentId);
}
