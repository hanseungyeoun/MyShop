package com.example.myshop.coupon.domain.coupon;

public interface CouponStore {

    Coupon store(Coupon coupon);

    void delete(Long couponId);
}
