package com.example.myshop.coupon.domain.Issued;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IssuedCouponStatus {
    NOT_USED("사용 가능"),
    USED("사용함"),
    EXPIRED("만료됨");

    private final String description;
}
