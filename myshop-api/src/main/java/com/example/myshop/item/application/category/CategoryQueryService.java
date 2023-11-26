package com.example.myshop.item.application.category;

import com.example.myshop.item.domain.category.CategoryInfo;

import java.util.List;

public interface CategoryQueryService {
    List<CategoryInfo> retrieveCategories(String parentPath);
}
