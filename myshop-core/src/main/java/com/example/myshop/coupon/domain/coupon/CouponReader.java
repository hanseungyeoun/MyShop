package com.example.myshop.coupon.domain.coupon;

import java.util.List;
import java.util.Optional;

public interface CouponReader {

    Coupon getCoupon(Long couponId);

    Optional<Coupon> findById(Long couponId);

    List<Coupon> findByIdIssueIdIn(List<Long> issueCouponId);

    List<Coupon> findByIdItemIdIn(Long itemId);
}
