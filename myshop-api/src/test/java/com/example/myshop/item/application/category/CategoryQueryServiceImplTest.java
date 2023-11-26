package com.example.myshop.item.application.category;

import com.example.myshop.IntegrationTest;
import com.example.myshop.item.domain.category.CategoryInfo;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryStore;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.myshop.CoreFixtures.aPCategory;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@IntegrationTest
class CategoryQueryServiceImplTest {

    @Autowired
    CategoryStore categoryStore;

    @Autowired
    CategoryQueryServiceImpl sut;
    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    @Test
    void 부모_경로로_조회() {
        //given
        List<Category> category = createCategory();

        //when
        List<CategoryInfo> categoryInfos = sut.retrieveCategories(category.get(0).getPath());

        //then
        for (CategoryInfo categoryInfo : categoryInfos) {
            System.out.println("categoryInfo = " + categoryInfo);
        }
        System.out.println("categoryInfos = " + categoryInfos);
        assertThat(categoryInfos).hasSize(3)
                .extracting(CategoryInfo::getName)
                .containsExactly("shirt", "sleeveless", "sweater");
    }

    private List<Category> createCategory() {
        //level1
        Category category1 = aPCategory().parentCategoryId(null).name("top").build();
        categoryJpaRepository.save(category1);

        //level2
        Category category2 = aPCategory().parentCategoryId(category1.getId()).name("shirt").build();
        Category category3 = aPCategory().parentCategoryId(category1.getId()).name("sleeveless").build();
        Category category4 = aPCategory().parentCategoryId(category1.getId()).name("sweater").build();
        categoryJpaRepository.saveAll(List.of(category2, category3, category4));

        //level3
        Category category5 = aPCategory().parentCategoryId(category4.getId()).name("sweatshirt").build();
        categoryStore.store(category5);

        category1.setPath(CATEGORY_PATH_DELIMITER, category1.getId());
        category2.setPath(category1.getPath(), category2.getId());
        category3.setPath(category1.getPath(), category3.getId());
        category4.setPath(category1.getPath(), category4.getId());
        category5.setPath(category4.getPath(), category5.getId());
        return categoryJpaRepository.saveAll(List.of(category1, category2));
    }
}