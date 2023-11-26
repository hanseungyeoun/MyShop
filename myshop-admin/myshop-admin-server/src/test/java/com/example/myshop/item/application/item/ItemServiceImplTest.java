package com.example.myshop.item.application.item;

import com.example.myshop.IntegrationTest;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.domain.item.ItemStore;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.myshop.Fixtures.*;
import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static com.example.myshop.item.application.item.ItemCommand.*;
import static org.assertj.core.api.Assertions.*;


@Transactional
@IntegrationTest
class ItemServiceImplTest {

    @Autowired
    ItemService sut;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    ItemStore itemStore;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    EntityManager em;


    @Test
    void registerItem_test() {
        //givn
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        RegisterItemCommand itemCommand = aItemCommand().build();

        //when
        Long itemId = sut.registerItem(itemCommand);

        //then
        Item findItem = itemJpaRepository.findById(itemId).get();
        assertThat(findItem.getId()).isEqualTo(itemId);
        assertThat(findItem.getItemName()).isEqualTo(itemCommand.getItemName());
    }

    @Test
    void  updateTest(){
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        UpdateItemCommand command = aUpdateItemCommand()
                        .itemName("이쁜 가방")
                        .itemOptionGroups(Collections.singletonList(
                            aUpdateItemOptionGroupCommand()
                                .ordering(2)
                                .itemOptionGroupName("컬러")
                                .itemOptions(Arrays.asList(
                                    aUpdateItemOptionCommand().ordering(1).itemOptionName("blue").build(),
                                    aUpdateItemOptionCommand().ordering(1).itemOptionName("red").build())
                                )
                                .build()
                        )).build();

        //when
        sut.updateItem(item.getId(), command);

        //then
        Item findItem = itemJpaRepository.findById(item.getId()).get();
        assertThat(item.getItemName()).isEqualTo(command.getItemName());

        assertThat(findItem)
                .extracting(Item::getItemOptionGroups, as(InstanceOfAssertFactories.COLLECTION))
                .extracting("itemOptionGroupName")
                .containsExactly("컬러");

    }

    private Item createItem(List<Category> categories) {
        List<ItemCategory> itemCategoryList = categories.stream().map(category -> aItemCategory().categoryId(category.getId()).build()).toList();

        Item item = anItem().itemCategories(itemCategoryList).build();
        item.changeOnSale();
        return itemStore.store(item);
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