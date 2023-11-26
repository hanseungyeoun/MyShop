package com.example.myshop.review.domain;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.OrderStatus;
import com.example.myshop.review.domain.ReviewGrade;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewInfo {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Main {
        private Long id;
        private Long memberId;
        private Long itemId;
        private String itemName;
        private Long orderId;
        private String reviewText;
        private String reviewGrade;
        private List<String> itemImages;
        private List<String> reviewImages;
        private List<OrderItemOptionGroupInfo> orderItemOptionGroupInfos;

        public Main(
                Long id,
                Long memberId,
                Long itemId,
                String itemName,
                Long orderId,
                String reviewText,
                ReviewGrade reviewGrade,
                List<String> itemImages,
                List<String> reviewImages
        ) {
            this.id = id;
            this.memberId = memberId;
            this.itemId = itemId;
            this.itemName = itemName;
            this.orderId = orderId;
            this.reviewText = reviewText;
            this.reviewGrade = reviewGrade.name();
            this.itemImages = itemImages;
            this.reviewImages = reviewImages;
        }
    }

    @Data
    @NoArgsConstructor
    public static class WriteAbleReviewInfo {
        private Long itemId;
        private Long memberId;
        private Long orderId;
        private OrderStatus status;
        private List<OrderItemInfo> orderItems;

        public WriteAbleReviewInfo(Long itemId, Long memberId, Long orderId, OrderStatus status) {
            this.itemId = itemId;
            this.memberId = memberId;
            this.orderId = orderId;
            this.status = status;
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class OrderItemInfo {
        private Long orderId;
        private Long orderItemId;
        private Integer orderCount;
        private Long itemId;
        private String itemName;
        private Integer totalAmount;
        private Integer itemPrice;
        private List<OrderItemOptionGroupInfo> orderItemOptionGroups;

        public OrderItemInfo(
                Long orderId,
                Long orderItemId,
                Integer orderCount,
                Long itemId,
                String itemName,
                Money totalAmount,
                Money itemPrice
        ) {
            this.orderId = orderId;
            this.orderItemId = orderItemId;
            this.orderCount = orderCount;
            this.itemId = itemId;
            this.itemName = itemName;
            this.totalAmount = totalAmount.getMoney().intValue();
            this.itemPrice = itemPrice.getMoney().intValue();
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class OrderItemOptionGroupInfo {
        private Long orderItemId;
        private Long optionGroupId;
        private Integer ordering;
        private String itemOptionGroupName;
        private List<OrderItemOptionInfo> orderItemOptions;

        public OrderItemOptionGroupInfo(
                Long orderItemId,
                Long optionGroupId,
                Integer ordering,
                String itemOptionGroupName
        ) {
            this.orderItemId = orderItemId;
            this.optionGroupId = optionGroupId;
            this.ordering = ordering;
            this.itemOptionGroupName = itemOptionGroupName;
        }
    }

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class OrderItemOptionInfo {
        private Long optionGroupId;
        private Long optionId;
        private Integer ordering;
        private String itemOptionName;

        public OrderItemOptionInfo(Long optionGroupId, Long optionId, Integer ordering, String itemOptionName) {
            this.optionGroupId = optionGroupId;
            this.optionId = optionId;
            this.ordering = ordering;
            this.itemOptionName = itemOptionName;
        }
    }
}
