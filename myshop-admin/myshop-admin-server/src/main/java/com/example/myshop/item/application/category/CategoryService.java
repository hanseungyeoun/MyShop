package com.example.myshop.item.application.category;

import static com.example.myshop.item.application.category.CategoryCommand.*;

public interface CategoryService {

    Long registerCategory(RegisterCategoryCommand command);

    void updateCategoryName(UpdateCategoryCommand command);

    void deleteCategoryDelete(Long categoryId);
}
