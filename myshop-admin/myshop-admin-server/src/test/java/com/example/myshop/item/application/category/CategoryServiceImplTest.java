package com.example.myshop.item.application.category;

import com.example.myshop.IntegrationTest;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryReader;
import com.example.myshop.item.domain.category.CategoryStore;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;

@IntegrationTest
@Transactional
class CategoryServiceImplTest {

    @Autowired
    CategoryReader categoryReader;

    @Autowired
    CategoryStore categoryStore;

    @Autowired
    CategoryServiceImpl sut;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Test
    void 부모_카테고리_등록() {
        //given
        Long parentId = null;
        String categoryName = "top";

        //when
        CategoryCommand.RegisterCategoryCommand command = new CategoryCommand.RegisterCategoryCommand(parentId, categoryName);
        Long savedId = sut.registerCategory(command);

        //then
        Category findCategory = categoryReader.getCategory(savedId);
        Assertions.assertThat(findCategory.getName()).isEqualTo(categoryName);
        Assertions.assertThat(findCategory.getPath()).isEqualTo(CATEGORY_PATH_DELIMITER +findCategory.getId());
    }

    @Test
    void 자식_카테고리_등록() {
        //given
        String parentName = "top";
        Category parentCategory = saveParent(parentName);

        //when
        String categoryName = "shirt";
        CategoryCommand.RegisterCategoryCommand command = new CategoryCommand.RegisterCategoryCommand(parentCategory.getId(), categoryName);
        Long savedId = sut.registerCategory(command);

        //then
        Category findCategory = categoryReader.getCategory(savedId);
        Assertions.assertThat(findCategory.getName()).isEqualTo("shirt");
        Assertions.assertThat(findCategory.getPath()).isEqualTo(parentCategory.getPath() + CATEGORY_PATH_DELIMITER + findCategory.getId());
    }

    @Test
    void 카테고리_이름_수정() {
        //given
        String name = "top";
        Category category = saveParent(name);

        //when
        String exceptionName = "shirt";
        CategoryCommand.UpdateCategoryCommand command = new CategoryCommand.UpdateCategoryCommand(category.getId(), exceptionName);
        sut.updateCategoryName(command);

        //then
        Category findCategory = categoryReader.getCategory(category.getId());
        Assertions.assertThat(findCategory.getName()).isEqualTo(exceptionName);
    }

    @Test
    void 카테고리_삭제() {
        //given
        String parentName = "top";
        Category parentCategory = saveParent(parentName);

        String categoryName = "shirt";
        CategoryCommand.RegisterCategoryCommand command = new CategoryCommand.RegisterCategoryCommand(parentCategory.getId(), categoryName);
        Long savedId = sut.registerCategory(command);

        //when
        sut.deleteCategoryDelete(parentCategory.getId());
        //then
        Assertions.assertThat(categoryJpaRepository.findAll()).isEmpty();
    }

    private Category saveParent(String name) {
        Long parentPath = null;
        String expectedName = name;
        Long storeCategoryId = sut.registerCategory(new CategoryCommand.RegisterCategoryCommand(parentPath, expectedName));
        return categoryReader.getCategory(storeCategoryId);
    }
}