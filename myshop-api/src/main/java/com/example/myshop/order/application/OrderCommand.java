package com.example.myshop.order.application;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.payment.PayMethod;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderCommand {
        private final Long memberId;
        private final String payMethod;
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
        private final List<RegisterOrderItemCommand> orderItems;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemCommand {
        private final Integer orderCount;
        private final Long itemId;
        private final String itemName;
        private final Money itemPrice;
        private final Long memberId;
        private final List<RegisterOrderItemOptionGroupCommand> orderItemOptionGroups;
        private List<Long> issueCouponIds;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemOptionGroupCommand {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterOrderItemOptionCommand> orderItemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItemOptionCommand {
        private final Integer ordering;
        private final String itemOptionName;
    }


    @Getter
    @Builder
    @ToString
    public static class PaymentCommand {
        private final Money amount;
        private final PayMethod payMethod;
        private String orderDescription;
    }

    @Getter
    @Builder
    @ToString
    public static class PaymentCancelCommand {
        private final Money amount;
        private final PayMethod payMethod;
        private String orderDescription;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateReceiverInfoCommand {
        private final String receiverName;
        private final String receiverPhone;
        private final String receiverZipcode;
        private final String receiverAddress1;
        private final String receiverAddress2;
        private final String etcMessage;
    }
}
