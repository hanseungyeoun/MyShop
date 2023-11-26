package com.example.myshop.coupon.application.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponType;
import lombok.*;

import java.time.LocalDate;
import java.util.List;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterCouponCommand {
        private final String couponName;
        private final Money discountAmount;
        private final Ratio discountRate;
        private final LocalDate expirationDate;
        private final List<Long> itemIds;
        private final CouponType couponType;

        public Coupon toEntity() {
            return couponType.createCoupon(couponName, discountAmount, discountRate, expirationDate, itemIds);
        }
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateCouponCommand {
        private final String couponName;
        private final Money discountAmount;
        private final Ratio discountRate;
        private final LocalDate expirationDate;
        private final List<Long> itemIds;
        private final CouponType couponType;
    }
}
