package com.example.myshop.order.domain.payment;

import com.example.myshop.coupon.domain.coupon.CouponType;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum PayMethod {
    CARD, KAKAO_PAY;

    @JsonCreator
    public static PayMethod from(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }
}
