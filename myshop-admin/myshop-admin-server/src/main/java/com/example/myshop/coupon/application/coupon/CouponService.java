package com.example.myshop.coupon.application.coupon;

import static com.example.myshop.coupon.application.coupon.CouponCommand.*;

public interface CouponService {
    Long registerCoupon(RegisterCouponCommand registerCouponCommand);
    void updateCoupon(Long couponId, UpdateCouponCommand command);
    void deleteCoupon(Long couponId);
}