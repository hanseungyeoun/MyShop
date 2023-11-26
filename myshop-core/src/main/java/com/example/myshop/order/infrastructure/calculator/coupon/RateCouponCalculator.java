package com.example.myshop.order.infrastructure.calculator.coupon;

import com.example.myshop.coupon.domain.coupon.CouponType;
import com.example.myshop.coupon.domain.coupon.RateCoupon;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.coupon.domain.coupon.Coupon;
import org.springframework.stereotype.Component;

@Component
public class RateCouponCalculator implements CouponCalculator {

    @Override
    public boolean isSupport(CouponType couponType) {
        return couponType == CouponType.FIXED_RATE;
    }

    @Override
    public Money calculation(Money totalAmount, Coupon coupon) {
        RateCoupon rateCoupon = (RateCoupon) coupon;
        Ratio discountRate = rateCoupon.getDiscountRate();
        return discountRate.of(totalAmount);
    }
}
