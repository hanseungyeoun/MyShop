package com.example.myshop.coupon.domain.Issued;

import java.util.List;
import java.util.Optional;

public interface IssuedCouponReader {

    IssuedCoupon getIssueCoupon(Long issueCouponId);

    List<IssuedCoupon> findByIdIn(List<Long> issueCouponIds);

    Optional<IssuedCoupon> findById(Long issueCouponId);

    List<IssuedCoupon> findItemIdAndMemberId(Long itemId, Long memberId);
}
