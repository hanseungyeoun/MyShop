package com.example.myshop.review.interfaces;

import com.example.myshop.order.domain.OrderStatus;
import com.example.myshop.order.interfaces.OrderResponseDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewResponseDto {

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Main {
        private final Long id;
        private final Long userId;
        private final Long itemId;
        private final String itemName;
        private final Long orderId;
        private final String reviewText;
        private final String reviewGrade;
        private final List<String> itemImages;
        private final List<String> reviewImages;
        private List<OrderItemOptionGroupInfo> orderItemOptionGroupInfos;
    }

    @Getter
    @Builder
    @ToString
    public static class WriteAbleReviewResponse {
        private final Long itemId;
        private final Long memberId;
        private final Long orderId;
        private final OrderStatus status;
        private final List<OrderItemInfo> orderItems;
    }

    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItemInfo {
        private final Integer orderCount;
        private final Long itemId;
        private final String itemName;
        private final Integer totalAmount;
        private final Long itemPrice;
        private final List<OrderResponseDto.OrderItemOptionGroup> orderItemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItemOptionGroupInfo {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<OrderResponseDto.OrderItemOptionInfo> orderItemOptions;
    }

    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItemOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
        private final Long itemOptionPrice;
    }

}
