package com.example.myshop.coupon.application.coupon;

import com.example.myshop.coupon.domain.coupon.CouponInfo;

import java.util.List;

public interface CouponQueryService {

    List<CouponInfo> retrieveCouponByItemId(Long itemId);

    CouponInfo retrieveCoupon(Long couponId);
}
