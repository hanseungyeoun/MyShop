package com.example.myshop.coupon.domain.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum CouponType {

    FIXED_RATE("정율 쿠폰"){
        public Coupon createCoupon(String couponName, Money money, Ratio ratio, LocalDate expirationDate, List<Long> itemIds) {
            return RateCoupon.RateCouponBuilder()
                    .couponName(couponName)
                    .discountRate(ratio)
                    .expirationDate(expirationDate)
                    .itemIds(itemIds)
                    .build();
        }
    },
    FIXED_AMOUNT("정액 쿠폰"){
        public Coupon createCoupon(String couponName, Money money, Ratio ratio, LocalDate expirationDate, List<Long> itemIds) {
            return AmountCoupon.AmountCouponBuilder()
                    .couponName(couponName)
                    .discountAmount(money)
                    .expirationDate(expirationDate)
                    .itemIds(itemIds)
                    .build();
        }
    },
    ZERO_DISCOUNT("기본 쿠폰"){
        public Coupon createCoupon(String couponName, Money money, Ratio ratio, LocalDate expirationDate, List<Long> itemIds) {
            return null;
        }
    };

    private final String description;

    public abstract Coupon createCoupon(String couponName, Money money, Ratio ratio, LocalDate expirationDate, List<Long> itemIds);

    public CouponItem createCouponItem(Long itemId) {
        return new CouponItem(itemId);
    }

    @JsonCreator
    public static CouponType from(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.toString().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
