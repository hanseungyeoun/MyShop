package com.example.myshop.service.issuedcoupon;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.IntegrationTest;
import com.example.myshop.config.TestJpaConfig;
import com.example.myshop.coupon.domain.coupon.AmountCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponStatus;
import com.example.myshop.coupon.infrastructure.coupon.CouponJpaRepository;
import com.example.myshop.coupon.infrastructure.Issuedcoupon.IssuedCouponJpaRepository;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.infrastructure.MemberJpaRepository;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.infrastructure.OrderJpaRepository;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static com.example.myshop.CoreFixtures.*;
import static com.example.myshop.constant.Constant.CATEGORY_PATH_DELIMITER;
import static org.assertj.core.api.Assertions.*;


@Transactional
@Import({TestJpaConfig.class})
@ContextConfiguration(classes = {CoreTestConfiguration.class})
@IntegrationTest
class ApplyIssuedCouponServiceTest {

    @Autowired
    ApplyIssuedCouponService sut;

    @Autowired
    ItemJpaRepository itemJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Autowired
    IssuedCouponJpaRepository issuedCouponJpaRepository;

    @Autowired
    CouponJpaRepository couponJpaRepository;

    @Autowired
    private InventoryJpaRepository inventoryJpaRepository;

    @Autowired
    private OrderJpaRepository orderJpaRepository;

    @Test
    void 쿠폰_사용_처리() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        AmountCoupon coupon = anAmountCoupon()
                .itemIds(List.of(item.getId()))
                .build();
        couponJpaRepository.save(coupon);

        IssuedCoupon issuedCoupon = aIssueCoupon().memberId(member.getId()).couponId(coupon.getId()).build();
        issuedCouponJpaRepository.save(issuedCoupon);

        Order order = createOrder(item, member, issuedCoupon);
        order.orderComplete();

        //when
        sut.applyIssuedCouponsByOrderId(order.getId());

        //then
        assertThat(issuedCoupon.getStatus()).isEqualTo(IssuedCouponStatus.USED);
        assertThat(issuedCoupon.getOrderId()).isEqualTo(order.getId());
    }

    @Test
    void 쿠폰_리셋_처리() {
        //given
        Member member = createMember();
        Item item = createItem(createCategory());
        createInventory(item.getId());

        AmountCoupon coupon = anAmountCoupon()
                .itemIds(List.of(item.getId()))
                .build();
        couponJpaRepository.save(coupon);

        IssuedCoupon issuedCoupon = aIssueCoupon().memberId(member.getId()).couponId(coupon.getId()).build();
        issuedCouponJpaRepository.save(issuedCoupon);

        Order order = createOrder(item, member, issuedCoupon);
        order.orderComplete();

        issuedCoupon.apply(order.getId());

        //when
        sut.resetIssuedCouponsByOrderId(order.getId());

        //then
        assertThat(issuedCoupon.getStatus()).isEqualTo(IssuedCouponStatus.NOT_USED);
        assertThat(issuedCoupon.getOrderId()).isNull();
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

    private Inventory createInventory(Long itemId) {
        Inventory inventory = aInventory().itemId(itemId).build();
        return inventoryJpaRepository.save(inventory);
    }

    private Order createOrder(Item item, Member member, IssuedCoupon issuedCoupon){
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
                                .issueCouponIds(Collections.singletonList(issuedCoupon.getId()))
                                .build()))
                .build();
        return orderJpaRepository.save(order);
    }

}