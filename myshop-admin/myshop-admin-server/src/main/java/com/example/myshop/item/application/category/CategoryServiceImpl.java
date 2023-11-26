package com.example.myshop.item.application.category;

import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryReader;
import com.example.myshop.item.domain.category.CategoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryReader categoryReader;
    private final CategoryStore categoryStore;

    @Override
    public Long registerCategory(CategoryCommand.RegisterCategoryCommand command) {
        String parentPath = getParentPath(command.getParentId());
        Category initCategory = new Category(command.getParentId(), command.getName());
        Category storeCategory = categoryStore.store(initCategory);
        Category category = categoryReader.getCategory(storeCategory.getId());
        category.setPath(parentPath, category.getId());
        return category.getId();
    }

    @Override
    public void updateCategoryName(CategoryCommand.UpdateCategoryCommand command) {
        Category category = categoryReader.getCategory(command.getCategoryId());
        category.changeCategoryName(command.getNewName());
    }

    @Override
    public void deleteCategoryDelete(Long categoryId) {
        categoryStore.deleteWithChild(categoryId);
    }

    private String getParentPath(Long parentId) {
        String parentPath = CATEGORY_PATH_DELIMITER;
        if (parentId != null) {
            Category parentCategory = categoryReader.getCategory(parentId);
            parentPath = parentCategory.getPath();
        }
        return parentPath;
    }
}
