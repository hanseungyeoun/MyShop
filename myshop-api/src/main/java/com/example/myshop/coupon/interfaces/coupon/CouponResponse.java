package com.example.myshop.coupon.interfaces.coupon;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CouponResponse {
    private final Long id;
    private final String couponName;
    private final Integer discountAmount;
    private final Double discountRate;
    private final LocalDate expirationDate;
    private final String couponType;
    private final String couponTypeDescription;
}
