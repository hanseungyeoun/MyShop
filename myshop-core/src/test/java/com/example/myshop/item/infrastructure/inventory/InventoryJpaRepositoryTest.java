package com.example.myshop.item.infrastructure.inventory;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.dto.Option;
import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.member.domain.Member;
import com.example.myshop.order.infrastructure.OrderJpaRepository;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

@Import({TestJpaConfig.class})
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class InventoryJpaRepositoryTest {

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    InventoryJpaRepository inventoryJpaRepository;

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Test
    void findByOptionGroupAndOptionInTest(){
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        //when
        OptionGroup size = new OptionGroup(1, "사이즈", List.of(new Option(1, "large")));
        OptionGroup color = new OptionGroup(1, "컬러", List.of(new Option(1, "blue")));
        Inventory findInventory = inventoryJpaRepository.findAllByItemIdAndOptionGroupIn(item.getId(), List.of(size, color)).get();

        //then
        System.out.println("findStock = " + findInventory);
        assertThat(findInventory.getId()).isEqualTo(inventory.getId());
    }

    @Test
    void getInventoryByItemIdTest(){
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        //when
        List<Inventory> findInventoryList = inventoryJpaRepository.findByItemId(item.getId());

        //then
        assertThat(findInventoryList).hasSize(1);
    }

    private Inventory createInventory(Long itemId) {
        Inventory inventory = aInventory().itemId(itemId).build();
        return inventoryJpaRepository.save(inventory);
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

    private Member createMember() {
        return memberJpaRepository.save(aMember().build());
    }
}