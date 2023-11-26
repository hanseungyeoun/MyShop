package com.example.myshop.item.interfaces.category;

import com.example.myshop.item.application.category.CategoryService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.example.myshop.item.application.category.CategoryCommand.*;
import static com.example.myshop.item.interfaces.category.CategoryDto.RegisterCategoryRequest;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public APIDataResponse<Long> registerCategory(@RequestBody @Valid RegisterCategoryRequest request) {
        var categoryId = categoryService.registerCategory(new RegisterCategoryCommand(request.getParentId(), request.getName()));
        return APIDataResponse.success(categoryId);
    }


    @PutMapping("/{categoryId}/newCategoryName")
    public APIDataResponse<String> updateCategoryName(@PathVariable Long categoryId, @RequestBody @Valid UpdateCategoryNameRequest request) {
        categoryService.updateCategoryName(new UpdateCategoryCommand(categoryId, request.getNewCategoryName()));
        return APIDataResponse.success("OK");
    }
}
