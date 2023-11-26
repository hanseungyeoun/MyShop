package com.example.myshop.coupon.infrastructure.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class IssuedCouponJpaStore implements IssuedCouponStore {

    private final IssuedCouponJpaRepository issuedCouponJpaRepository;

    @Override
    public IssuedCoupon store(IssuedCoupon issuedCoupon) {
        return issuedCouponJpaRepository.save(issuedCoupon);
    }
}
