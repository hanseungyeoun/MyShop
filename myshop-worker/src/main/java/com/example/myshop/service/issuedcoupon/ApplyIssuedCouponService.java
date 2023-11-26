package com.example.myshop.service.issuedcoupon;


public interface ApplyIssuedCouponService {

    void applyIssuedCouponsByOrderId(Long orderId);

    void resetIssuedCouponsByOrderId(Long orderId);
}
