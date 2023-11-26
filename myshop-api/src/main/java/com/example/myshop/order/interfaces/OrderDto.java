package com.example.myshop.order.interfaces;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.payment.PayMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderRequest {
        @NotNull(message = "userId 는 필수값입니다")
        private Long memberId;

        @NotBlank(message = "payMethod 는 필수값입니다")
        private String payMethod;

        @NotBlank(message = "receiverName 는 필수값입니다")
        private String receiverName;

        @NotBlank(message = "receiverPhone 는 필수값입니다")
        private String receiverPhone;

        @NotBlank(message = "receiverZipcode 는 필수값입니다")
        private String receiverZipcode;

        @NotBlank(message = "receiverAddress1 는 필수값입니다")
        private String receiverAddress1;

        @NotBlank(message = "receiverAddress2 는 필수값입니다")
        private String receiverAddress2;

        @NotBlank(message = "etcMessage 는 필수값입니다")
        private String etcMessage;
        @NotNull
        private List<RegisterOrderItemRequest> orderItems = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItemRequest {
        @NotNull(message = "orderCount 는 필수값입니다")
        private Integer orderCount;

        @NotNull(message = "itemToken 는 필수값입니다")
        private Long itemId;

        @NotBlank(message = "itemName 는 필수값입니다")
        private String itemName;

        @NotNull(message = "itemPrice 는 필수값입니다")
        private Integer itemPrice;

        @NotNull(message = "memberId 는 필수값입니다")
        private Long memberId;
        @NotNull
        private List<RegisterOrderItemOptionGroupRequest> orderItemOptionGroups = new ArrayList<>();
        List<Long> issueCouponIds;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItemOptionGroupRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        private Integer ordering;

        @NotBlank(message = "itemOptionGroupName 는 필수값입니다")
        private String itemOptionGroupName;

        private List<RegisterOrderItemOptionRequest> orderItemOptions = new ArrayList<>();
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterOrderItemOptionRequest {
        @NotNull(message = "ordering 는 필수값입니다")
        private Integer ordering;

        @NotBlank(message = "itemOptionName 는 필수값입니다")
        private String itemOptionName;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderResponse {
        private final Long id;
    }

    @Getter
    @Setter
    @ToString
    public static class PaymentRequest {
        @NotNull(message = "payMethod 는 필수값입니다")
        private PayMethod payMethod;

        @NotNull(message = "amount 는 필수값입니다")
        private Integer amount;

        @NotBlank(message = "orderDescription 는 필수값입니다")
        private String orderDescription;
    }

    @Getter
    @Setter
    @ToString
    public static class PaymentCancelRequest {
        @NotNull(message = "payMethod 는 필수값입니다")
        private PayMethod payMethod;

        @NotNull(message = "amount 는 필수값입니다")
        private Integer amount;

        @NotBlank(message = "orderDescription 는 필수값입니다")
        private String orderDescription;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateReceiverInfoRequest {
        private String receiverName;
        private String receiverPhone;
        private String receiverZipcode;
        private String receiverAddress1;
        private String receiverAddress2;
        private String etcMessage;
    }
}
