package com.example.myshop.item.application.item;

import com.example.myshop.IntegrationTest;
import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class ItemQueryServiceImplTest {

    @Autowired
    ItemQueryServiceImpl sut;

    @Autowired
    ItemJpaRepository itemJpaRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    EntityManager em;

    @Test
    void 아이템_조회(){
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);

        //when
        ItemInfo.Main itemInfo = sut.retrieveItem(item.getId());

        //then
        System.out.println("findItem = " + itemInfo);
        assertThat(itemInfo.getItemId()).isEqualTo(item.getId());

        assertThat(itemInfo)
                .extracting(ItemInfo.Main::getItemOptionGroups, as(InstanceOfAssertFactories.COLLECTION))
                .hasSize(2)
                .extracting("itemOptionGroupName")
                .containsExactly("사이즈", "컬러");

        assertThat(itemInfo)
                .extracting(ItemInfo.Main::getCategoryInfos, as(InstanceOfAssertFactories.COLLECTION))
                .hasSize(2)
                .extracting("name")
                .containsExactly("top", "shirt");
    }

    private Item createItem(List<Category> categories) {
        List<ItemCategory> itemCategoryList = categories.stream().map(category -> aItemCategory().categoryId(category.getId()).build()).toList();

        Item item = anItem().itemCategories(itemCategoryList).build();
        item.changeOnSale();
        return itemJpaRepository.save(item);
    }

    private List<Category> createCategory(){
        Category category1 = aPCategory().parentCategoryId(null).name("top").build();
        categoryJpaRepository.save(category1);
        Category category2 = aPCategory().parentCategoryId(category1.getId()).name("shirt").build();
        categoryJpaRepository.save(category2);

        category1.setPath(CATEGORY_PATH_DELIMITER, category1.getId());
        category2.setPath(category1.getPath(), category2.getId());
        return categoryJpaRepository.saveAll(List.of(category1, category2));
    }

}