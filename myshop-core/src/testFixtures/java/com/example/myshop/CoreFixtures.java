package com.example.myshop;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Password;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.common.jpa.RoleType;
import com.example.myshop.coupon.domain.coupon.AmountCoupon;
import com.example.myshop.coupon.domain.coupon.CouponItem;
import com.example.myshop.coupon.domain.coupon.RateCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.member.domain.Member;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOption;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryOption;
import com.example.myshop.item.domain.inventory.InventoryOptionGroup;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.domain.item.ItemOption;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewGrade;
import com.example.myshop.review.domain.ReviewImage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.example.myshop.item.domain.inventory.Inventory.InventoryBuilder;
import static com.example.myshop.item.domain.inventory.InventoryOption.InventoryOptionBuilder;
import static com.example.myshop.item.domain.inventory.InventoryOptionGroup.InventoryOptionGroupBuilder;

public class CoreFixtures {
    /*
     * 재고
     */
    public static InventoryBuilder aInventory() {
        return Inventory.builder()
                .quantity(10)
                .itemId(1L)
                .inventoryItemOptionGroups(Arrays.asList(
                        aInventoryGroupOption()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .inventoryOptions(Collections.singletonList(aInventoryOption().ordering(1).itemOptionName("large").build()))
                                .build(),
                        aInventoryGroupOption()
                                .ordering(1)
                                .itemOptionGroupName("컬러")
                                .inventoryOptions(Collections.singletonList(aInventoryOption().ordering(1).itemOptionName("blue").build()))
                                .build()
                ));
    }

    public static InventoryOptionGroupBuilder aInventoryGroupOption() {
        return InventoryOptionGroup.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .inventoryOptions(Collections.singletonList(aInventoryOption().build()));
    }

    public static InventoryOptionBuilder aInventoryOption() {
        return InventoryOption.builder().ordering(1).itemOptionName("large");
    }


    /*
     * 리뷰
     */
    public static Review.ReviewBuilder aReview() {
        return Review.builder()
                .memberId(1L)
                .orderId(1L)
                .itemId(1L)
                .reviewText("옷이 좋아요")
                .reviewGrade(ReviewGrade.ONE)
                .reviewImages(List.of(aReviewImage().imagePath("image.jpg").build()));
    }

    public static ReviewImage.ReviewImageBuilder aReviewImage() {
        return ReviewImage.builder();
    }

    /*
     * 상품
     */
    public static Item.ItemBuilder anItem() {
        return Item.builder()
                .itemName("가방")
                .itemPrice(Money.valueOf(10000))
                .itemDetails("비싼 가방 ")
                .itemImages(new ArrayList<>())
                .itemCategories(Collections.singletonList(aItemCategory().build()))
                .optionGroups(Arrays.asList(
                        aItemOptionGroup()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .itemOptions(Collections.singletonList(aItemOption().ordering(1).itemOptionName("large").build()))
                                .build(),
                        aItemOptionGroup()
                                .ordering(2)
                                .itemOptionGroupName("컬러")
                                .itemOptions(Collections.singletonList(aItemOption().ordering(1).itemOptionName("blue").build()))
                                .build()));
    }

    public static ItemCategory.ItemCategoryBuilder aItemCategory() {
        return ItemCategory.builder().categoryId(1L);
    }

    public static Category.CategoryBuilder aPCategory() {
        return Category.builder().parentCategoryId(null).name("top");
    }

    public static ItemOptionGroup.ItemOptionGroupBuilder aItemOptionGroup() {
        return ItemOptionGroup.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .itemOptions(Collections.singletonList(aItemOption().build()));
    }

    public static ItemOption.ItemOptionBuilder aItemOption() {
        return ItemOption.builder().ordering(1).itemOptionName("100");
    }

    public static Order.OrderBuilder anOrder() {
        return Order.builder()
                .memberId(1L)
                .payMethod("CARD")
                .deliveryFragment(DeliveryFragment.builder()
                        .receiverName("전지현")
                        .receiverAddress1("성남시")
                        .receiverAddress2("수정구")
                        .receiverPhone("010")
                        .receiverZipcode("100")
                        .etcMessage("없어요")
                        .build()
                )
                .orderItems(List.of(anOrderItem().build()));
    }

    public static OrderItem.OrderItemBuilder anOrderItem() {
        return OrderItem.builder()
                .orderCount(1)
                .itemId(1L)
                .memberId(1L)
                .itemName("가방")
                .itemPrice(Money.valueOf(10000))
                .issueCouponIds(List.of())
                .orderItemOptionGroups(Arrays.asList(
                        anOrderOptionGroup()
                                .ordering(1)
                                .itemOptionGroupName("사이즈")
                                .orderItemOptions(Collections.singletonList(anOrderOption().ordering(1).itemOptionName("large").build()))
                                .build()
                        ,
                        anOrderOptionGroup()
                                .ordering(2)
                                .itemOptionGroupName("컬러")
                                .orderItemOptions(Collections.singletonList(anOrderOption().ordering(1).itemOptionName("blue").build()))
                                .build()));
    }

    public static OrderItemOptionGroup.OrderItemOptionGroupBuilder anOrderOptionGroup() {
        return OrderItemOptionGroup.builder()
                .ordering(1)
                .itemOptionGroupName("사이즈")
                .orderItemOptions(Collections.singletonList(anOrderOption().build()));
    }

    public static OrderItemOption.OrderItemOptionBuilder anOrderOption() {
        return OrderItemOption.builder()
                .ordering(1)
                .itemOptionName("large");
    }
    //  쿠폰
    public static RateCoupon.RateCouponBuilder aRateCoupon() {
        return RateCoupon.RateCouponBuilder()
                .couponName("정율 쿠폰 ")
                .discountRate(Ratio.valueOf(0.1))
                .expirationDate(LocalDate.now().plusDays(10))
                .itemIds(List.of(1L));
    }

    public static AmountCoupon.AmountCouponBuilder anAmountCoupon() {
        return AmountCoupon.AmountCouponBuilder()
                .couponName("정율 쿠폰 ")
                .discountAmount(Money.valueOf(1000))
                .expirationDate(LocalDate.now().plusDays(10))
                .itemIds(List.of(1L));
    }

    public static CouponItem.CouponItemBuilder aCouponItem() {
        return CouponItem.builder()
                .itemId(1L);
    }

    public static IssuedCoupon.IssuedCouponBuilder aIssueCoupon(){
        return IssuedCoupon.builder()
                .couponId(1L)
                .memberId(1L)
                .expirationDate(LocalDate.now().plusDays(10));
    }

    //member
    public static Member.MemberBuilder aMember(){
        return Member.builder()
            .username("userId")
            .password(new Password("pass"))
            .role(RoleType.ROLE_USER);
    }
}
