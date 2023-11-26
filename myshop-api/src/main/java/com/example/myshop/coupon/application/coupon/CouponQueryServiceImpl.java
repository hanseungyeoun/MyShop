package com.example.myshop.coupon.application.coupon;

import com.example.myshop.coupon.domain.coupon.CouponInfo;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CouponQueryServiceImpl implements CouponQueryService{

    private final CouponReader couponReader;

    public List<CouponInfo> retrieveCouponByItemId(Long itemId) {
        List<Coupon> coupons = couponReader.findByIdItemIdIn(itemId);
        return coupons.stream().map(CouponInfo::fromEntity).toList();
    }

    public CouponInfo retrieveCoupon(Long couponId) {
        Coupon coupon = couponReader.getCoupon(couponId);
        return CouponInfo.fromEntity(coupon);
    }
}
