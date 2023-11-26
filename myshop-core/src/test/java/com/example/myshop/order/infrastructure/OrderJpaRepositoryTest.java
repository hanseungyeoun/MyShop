package com.example.myshop.order.infrastructure;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.enums.OrderSearchType;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.dto.OrderSearchCondition;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.infrastructure.category.category.CategoryJpaRepository;
import com.example.myshop.item.infrastructure.inventory.InventoryJpaRepository;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.assertThat;

@Import({TestJpaConfig.class})
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class OrderJpaRepositoryTest {

    @Autowired
    OrderJpaRepository orderJpaRepository;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    InventoryJpaRepository inventoryJpaRepository;


    @Test
    void orderSaveTest() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);

        //when
        Order findOrder = orderJpaRepository.findById(order.getId()).get();

        //then
        assertThat(findOrder.getId()).isEqualTo(order.getId());
    }

    @Test
    void 주문_번호로_주문_조회() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);

        //when
        OrderSearchCondition cond = OrderSearchCondition.builder()
                .searchType(OrderSearchType.ORDER_ID)
                .searchValue(order.getId().toString())
                .startDatetime(LocalDate.now().minusDays(10))
                .endDatetime(LocalDate.now().plusDays(10))
                .build();

        Page<OrderInfo.Main> orders =
                orderJpaRepository.findOrderAll(cond, member.getId(), PageRequest.of(0, 10));
        System.out.println("order.getContent() = " + orders.getContent());

        //then
        assertThat(orders.getContent()).hasSize(1);
        assertThat(orders.getContent())
                .extracting(OrderInfo.Main::getOrderId)
                .containsExactly(order.getId());
    }

    @Test
    void 상품으로_주문_조회() {
        //given
        Member member = createMember();
        List<Category> categories = createCategory();
        Item item = createItem(categories);
        Order order = createOrder(item, member);

        //when
        OrderSearchCondition cond = OrderSearchCondition.builder()
                .searchType(OrderSearchType.ITEM_NAME)
                .searchValue(item.getItemName())
                .startDatetime(LocalDate.now().minusDays(10))
                .endDatetime(LocalDate.now().plusDays(10))
                .build();

        Page<OrderInfo.Main> orders =
                orderJpaRepository.findOrderAll(cond, member.getId(), PageRequest.of(0, 10));
        System.out.println("order.getContent() = " + orders.getContent());

        //then
        assertThat(orders.getContent()).hasSize(1);
        assertThat(orders.getContent())
                .extracting(OrderInfo.Main::getOrderId)
                .containsExactly(order.getId());
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

    private List<Category> createCategory() {
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

    Order createOrder(Item item, Member member) {
        OrderItem orderItem = anOrderItem()
                .orderCount(1)
                .itemId(item.getId())
                .itemName(item.getItemName())
                .memberId(member.getId())
                .itemPrice(item.getItemPrice())
                .build();

        Order order = anOrder()
                .memberId(member.getId())
                .orderItems(Collections.singletonList(
                    anOrderItem()
                        .itemName(item.getItemName())
                        .memberId(member.getId())
                        .itemPrice(item.getItemPrice())
                        .itemId(item.getId())
                        .build()))
                .build();
        return orderJpaRepository.save(order);
    }
}