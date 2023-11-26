package com.example.myshop.service.inventory;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.IntegrationTest;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.inventory.InventoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

@Import({TestJpaConfig.class})
@ContextConfiguration(classes = {CoreTestConfiguration.class})
@IntegrationTest
class ChangeQuantityInventoryServiceTest {

    @Autowired
    IncreaseQuantityInventoryService sut;


    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    InventoryJpaRepository inventoryJpaRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void increaseInventoryQuantity() throws InterruptedException {
        //given
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId());

        //when
        sut.increaseQuantity(inventory.getId(), 1);

        //then
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        assertThat(findInventory.getQuantity()).isEqualTo(11);
    }

    @Test
    void 동시에_10개_요청() throws InterruptedException {
        //given
        int initQuantity = 10;
        int threadCount = 10;
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Inventory inventory = createInventory(item.getId(), initQuantity);

        //when
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    sut.increaseQuantity(inventory.getId(), 1);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        Inventory findInventory = inventoryJpaRepository.findById(inventory.getId()).get();
        assertThat(findInventory.getQuantity()).isEqualTo(20);
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

    private Inventory createInventory(Long itemId) {
        Inventory inventory = aInventory().itemId(itemId).build();
        return inventoryJpaRepository.save(inventory);
    }

    private Inventory createInventory(Long itemId, int quantity) {
        Inventory inventory = aInventory().itemId(itemId).quantity(quantity).build();
        return inventoryJpaRepository.save(inventory);
    }
}