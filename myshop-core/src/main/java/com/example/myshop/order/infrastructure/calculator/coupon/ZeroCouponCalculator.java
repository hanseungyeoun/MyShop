package com.example.myshop.order.infrastructure.calculator.coupon;


import com.example.myshop.coupon.domain.coupon.CouponType;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.Coupon;
import org.springframework.stereotype.Component;

@Component
public class ZeroCouponCalculator implements CouponCalculator {

    @Override
    public boolean isSupport(CouponType couponType) {
        return couponType == CouponType.ZERO_DISCOUNT;
    }

    @Override
    public Money calculation(Money totalAmount, Coupon coupon) {
        return Money.ZERO;
    }
}
