package com.example.myshop.coupon.infrastructure.coupon;

import com.example.myshop.coupon.domain.coupon.CouponStore;
import com.example.myshop.coupon.domain.coupon.Coupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponJpaStore implements CouponStore {

    private final CouponJpaRepository couponRepository;

    @Override
    public Coupon store(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public void delete(Long couponId) {
        couponRepository.deleteById(couponId);
    }
}
