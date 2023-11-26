package com.example.myshop.item.interfaces.category;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDto {

    @Getter
    @ToString
    @AllArgsConstructor
    public static class RegisterCategoryRequest {
        private Long parentId;

        @NotBlank
        private String name;

    }

    @Getter
    @ToString
    public static class RegisterCategoryResponse {
        private Long id;
    }

}
