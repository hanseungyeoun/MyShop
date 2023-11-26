package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IssuedCouponResponse {
    private final Long id;
    private final Long couponId;
    private final Long memberId;
    private final LocalDate expirationDate;
    private final Long orderId;
    private final String issueCouponType;
    private final String issueCouponTypeDescription;
}
