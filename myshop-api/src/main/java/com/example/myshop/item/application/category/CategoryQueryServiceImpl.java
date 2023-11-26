package com.example.myshop.item.application.category;

import com.example.myshop.item.domain.category.CategoryInfo;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {

    private final CategoryReader categoryReader;

    @Override
    public List<CategoryInfo> retrieveCategories(String parentPath) {
        List<Category> categories = categoryReader.getCategoryParentPath(parentPath);
        List<CategoryInfo> categoryInfos = CategoryInfo.fromEntity(categories);
        return categoryInfos;
    }
}
