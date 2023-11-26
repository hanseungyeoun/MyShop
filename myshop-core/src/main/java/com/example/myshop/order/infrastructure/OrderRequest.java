package com.example.myshop.order.infrastructure;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.order.domain.payment.PayMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class OrderRequest {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class PaymentRequest {
        private final Money amount;
        private final PayMethod payMethod;
        private final String orderDescription;
    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class PaymentCancelRequest {
        private final Money amount;
        private final PayMethod payMethod;
        private final String orderDescription;
    }
}
