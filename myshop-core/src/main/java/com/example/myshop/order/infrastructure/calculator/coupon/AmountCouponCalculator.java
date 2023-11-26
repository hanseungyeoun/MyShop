package com.example.myshop.order.infrastructure.calculator.coupon;

import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponType;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.AmountCoupon;
import org.springframework.stereotype.Component;

@Component
public class AmountCouponCalculator implements CouponCalculator {

    @Override
    public boolean isSupport(CouponType couponType) {
        return couponType == CouponType.FIXED_AMOUNT;
    }

    @Override
    public Money calculation(Money totalAmount, Coupon coupon) {
        AmountCoupon amountCoupon = (AmountCoupon)coupon;
        return amountCoupon.getDiscountAmount();
    }
}
