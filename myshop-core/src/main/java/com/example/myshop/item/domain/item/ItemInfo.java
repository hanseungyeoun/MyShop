package com.example.myshop.item.domain.item;

import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.item.infrastructure.item.ItemStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemInfo {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Main {
        private Long itemId;
        private String itemName;
        private Integer itemPrice;
        private String itemDetails;
        private String itemStatus;
        private String itemStatusDescription;
        private List<String> itemImages;
        private List<CouponInfo> couponInfos;
        private List<CategoryInfo> categoryInfos;
        private List<ItemOptionGroupInfo> itemOptionGroups;

        public Main(
                Long itemId,
                String itemName,
                Money itemPrice,
                String itemDetails,
                ItemStatus status
        ) {
            this.itemId = itemId;
            this.itemName = itemName;
            this.itemPrice = itemPrice.getMoney().intValue();
            this.itemDetails = itemDetails;
            this.itemStatus = status.name();
            this.itemStatusDescription = status.getDescription();
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class ItemOptionGroupInfo {
        private Long itemId;
        private Long itemOptionGroupId;
        private Integer ordering;
        private String itemOptionGroupName;
        private List<ItemOptionInfo> itemOptions;

        public ItemOptionGroupInfo(
                Long itemId,
                Long itemOptionGroupId,
                Integer ordering,
                String itemOptionGroupName
        ) {
            this.itemId = itemId;
            this.itemOptionGroupId = itemOptionGroupId;
            this.ordering = ordering;
            this.itemOptionGroupName = itemOptionGroupName;
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class ItemOptionInfo {
        private Long itemOptionGroupId;
        private Long itemOptionId;
        private Integer ordering;
        private String itemOptionName;

        public ItemOptionInfo
                (Long itemOptionGroupId,
                 Long itemOptionId,
                 Integer ordering,
                 String itemOptionName
        ) {
            this.itemOptionGroupId = itemOptionGroupId;
            this.itemOptionId = itemOptionId;
            this.ordering = ordering;
            this.itemOptionName = itemOptionName;
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class CouponInfo {
        private Long couponId;
        private String couponName;
        private Integer discountAmount;
        private Double discountRate;
        private LocalDate expirationDate;
        private String couponType;
        private String couponTypeDescription;

        public CouponInfo(
                Long couponId,
                String couponName,
                Money discountAmount,
                Ratio discountRate,
                LocalDate expirationDate,
                String couponType,
                String couponTypeDescription
        ) {
            this.couponId = couponId;
            this.couponName = couponName;
            this.discountAmount = discountAmount.getMoney().intValue();
            this.discountRate = discountRate.getRate();
            this.expirationDate = expirationDate;
            this.couponType = couponType;
            this.couponTypeDescription = couponTypeDescription;
        }
    }


    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class CategoryInfo {
        private Long itemId;
        private Long categoryId;
        private Long parentCategoryId;
        private String path;
        private String name;

        public CategoryInfo(Long itemId, Long categoryId, Long parentCategoryId, String path, String name) {
            this.itemId = itemId;
            this.categoryId = categoryId;
            this.parentCategoryId = parentCategoryId;
            this.path = path;
            this.name = name;
        }
    }
}
