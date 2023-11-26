package com.example.myshop.order.domain;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.OrderStatus;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.DeliveryStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderInfo {

    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    public static class Main {
        private Long orderId;
        private Long memberId;
        private String payMethod;
        private Integer totalAmount;
        private DeliveryInfo deliveryInfo;
        private LocalDateTime orderedAt;
        private String status;
        private String statusDescription;
        private List<OrderItemInfo> orderItems;

        public Main(
                Long orderId,
                Long userId,
                String payMethod,
                Money totalAmount,
                DeliveryFragment deliveryInfo,
                LocalDateTime orderedAt,
                OrderStatus status
        ) {
            this.orderId = orderId;
            this.memberId = userId;
            this.payMethod = payMethod;
            this.totalAmount = totalAmount.getMoney().intValue();
            this.deliveryInfo = DeliveryInfo.of(deliveryInfo);
            this.orderedAt = orderedAt;
            this.status = status.name();
            this.statusDescription = status.getDescription();
        }
    }

    @Setter
    @Getter
    @Builder
    @ToString
    public static class DeliveryInfo {
        private String receiverName;
        private String receiverPhone;
        private String receiverZipcode;
        private String receiverAddress1;
        private String receiverAddress2;
        private String etcMessage;


        public static DeliveryInfo of(DeliveryFragment deliveryFragment) {
            return DeliveryInfo.builder()
                    .receiverName(deliveryFragment.getReceiverName())
                    .receiverPhone(deliveryFragment.getReceiverPhone())
                    .receiverZipcode(deliveryFragment.getReceiverZipcode())
                    .receiverAddress1(deliveryFragment.getReceiverAddress1())
                    .receiverAddress2(deliveryFragment.getReceiverAddress2())
                    .etcMessage(deliveryFragment.getEtcMessage())
                    .build();
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
        private Long memberId;
        private Integer totalAmount;
        private Integer itemPrice;
        private String deliveryStatus;
        private String deliveryStatusDescription;
        private List<OrderItemOptionGroupInfo> orderItemOptionGroups;

        public OrderItemInfo(
                Long orderId,
                Long orderItemId,
                Integer orderCount,
                Long itemId,
                String itemName,
                Long memberId,
                Money totalAmount,
                Money itemPrice,
                DeliveryStatus deliveryStatus
        ) {
            this.orderId = orderId;
            this.orderItemId = orderItemId;
            this.orderCount = orderCount;
            this.itemId = itemId;
            this.itemName = itemName;
            this.memberId = memberId;
            this.totalAmount = totalAmount.getMoney().intValue();
            this.itemPrice = itemPrice.getMoney().intValue();
            this.deliveryStatus = deliveryStatus.name();
            this.deliveryStatusDescription = deliveryStatus.getDescription();
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
