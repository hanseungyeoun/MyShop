package com.example.myshop.order.infrastructure.calculator.coupon;

import com.example.myshop.coupon.domain.coupon.CouponType;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.Coupon;

public interface CouponCalculator {

    boolean isSupport(CouponType couponType);

    Money calculation(Money totalAmount, Coupon coupon);
}
