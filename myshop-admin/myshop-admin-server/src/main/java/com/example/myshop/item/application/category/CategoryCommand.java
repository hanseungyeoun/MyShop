package com.example.myshop.item.application.category;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryCommand {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class RegisterCategoryCommand {
        private final Long parentId;
        private final String name;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class UpdateCategoryCommand {
        private final Long categoryId;
        private final String newName;
    }
}
