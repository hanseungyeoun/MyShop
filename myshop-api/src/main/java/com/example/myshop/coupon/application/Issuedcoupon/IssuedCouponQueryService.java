package com.example.myshop.coupon.application.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCouponInfo;

import java.util.List;

public interface IssuedCouponQueryService {

    List<IssuedCouponInfo> retrieveIssue(Long itemId, Long memberId);
}
