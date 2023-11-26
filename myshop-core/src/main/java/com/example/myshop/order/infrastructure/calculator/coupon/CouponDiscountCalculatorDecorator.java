package com.example.myshop.order.infrastructure.calculator.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.order.infrastructure.calculator.DiscountCalculatorDecorator;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CouponDiscountCalculatorDecorator implements DiscountCalculatorDecorator {

    private final List<CouponCalculator> calculators;
    private final List<Coupon> coupons;

    @Override
    public Money calculateDiscount(Money totalAmount) {
        return totalAmount.minus(Money.sum(coupons, coupon -> calculate(totalAmount, coupon)));
    }

    private Money calculate(Money totalAmount, Coupon coupon) {
        CouponCalculator couponCalculator = calculators.stream()
                .filter(cal -> cal.isSupport(coupon.getCouponType()))
                .findFirst()
                .orElseThrow();

        return couponCalculator.calculation(totalAmount, coupon);
    }
}
