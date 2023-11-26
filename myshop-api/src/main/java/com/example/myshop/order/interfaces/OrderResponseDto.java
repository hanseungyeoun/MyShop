package com.example.myshop.order.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderResponseDto {
    // 조회
    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Main {
        private final Long id;
        private final Long userId;
        private final String payMethod;
        private final Integer totalAmount;
        private final DeliveryInfo deliveryInfo;
        private final String orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<OrderItemInfo> orderItems;

    }

    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class DeliveryInfo {
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
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
        private final String deliveryStatus;
        private final String deliveryStatusDescription;
        private final List<OrderItemOptionGroup> orderItemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class OrderItemOptionGroup {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<OrderItemOptionInfo> orderItemOptions;
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
