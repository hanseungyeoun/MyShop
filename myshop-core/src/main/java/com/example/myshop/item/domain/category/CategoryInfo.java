package com.example.myshop.item.domain.category;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class CategoryInfo {
    private Long id;
    private Long parentCategoryId;
    private String path;
    private String name;
    private LocalDateTime createdAt;
    private boolean hasPrevious;
    private List<CategoryInfo> childCategories = new ArrayList<>();


    public CategoryInfo(Long id, Long parentCategoryId, String path, String name, LocalDateTime createdAt) {
        this.id = id;
        this.parentCategoryId = parentCategoryId;
        this.path = path;
        this.name = name;
        this.createdAt = createdAt;
    }

    public static List<CategoryInfo> fromEntity(List<Category> categories) {
        Map<Long, CategoryInfo> map = categories.stream().map(CategoryInfo::fromEntity).collect(Collectors.toMap(CategoryInfo::getId, Function.identity()));

        map.values().stream().filter(CategoryInfo::hasParentCategoryId).forEach(category -> {
            CategoryInfo parentCategory = map.getOrDefault(category.getParentCategoryId(), null);
            if (parentCategory != null) {
                category.setHasPrevious(true);
                parentCategory.getChildCategories().add(category);
            } else {
                category.setHasPrevious(false);
            }
        });

        return map.values().stream().filter(category -> !category.isHasPrevious()).sorted(Comparator.comparing(CategoryInfo::getCreatedAt).thenComparingLong(CategoryInfo::getId)).toList();
    }

    public static CategoryInfo fromEntity(Category category) {
        return new CategoryInfo(category.getId(), category.getParentCategoryId(), category.getPath(), category.getName(), category.getCreatedAt());
    }

    private boolean hasParentCategoryId() {
        return this.parentCategoryId != null;
    }
}
