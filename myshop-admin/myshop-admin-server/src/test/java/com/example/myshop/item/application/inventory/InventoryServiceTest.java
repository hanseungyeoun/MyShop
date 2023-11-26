package com.example.myshop.item.application.inventory;

import com.example.myshop.IntegrationTest;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemStore;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.inventory.InventoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static com.example.myshop.Fixtures.aInventoryCommand;
import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static com.example.myshop.item.application.inventory.InventoryCommand.*;
import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
@Transactional
class InventoryServiceTest {

    @Autowired
    InventoryService inventoryService;


    @Autowired
    InventoryJpaRepository inventoryJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    ItemStore itemStore;


    @Test
    void registerInventory() {
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        RegisterInventoryCommand command = aInventoryCommand().build();
        Long registerInventoryId= inventoryService.registerInventory(command);

        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        assertThat(findInventory).isNotNull();
    }

    @Test
    void updateInventoryQuantity() {
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        //when
        inventoryService.updateQuantity(inventory.getId(), 1);

        //then
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        assertThat(findInventory.getQuantity()).isEqualTo(1);
    }

    @Test
    void deleteInventory() {
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        //when
        inventoryService.deleteInventory(inventory.getId());

        //then
        List<Inventory> stocks = inventoryJpaRepository.findAll();
        assertThat(stocks).isEmpty();
    }


    private final List<Integer> itemAmounts = Arrays.asList(1, 1, 1, 1, 1);



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

    private Inventory createInventory(Long itemId) {
        Inventory inventory = aInventory().itemId(itemId).build();
        return inventoryJpaRepository.save(inventory);
    }
}