package com.example.myshop.coupon.infrastructure.coupon;

import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import com.example.myshop.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CouponJpaReader implements CouponReader {

    public final CouponJpaRepository couponRepository;

    @Override
    public Coupon getCoupon(Long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("coupon id not found %d", couponId)));
    }

    @Override
    public Optional<Coupon> findById(Long couponId) {
        return couponRepository.findById(couponId);
    }

    @Override
    public List<Coupon> findByIdIssueIdIn(List<Long> issueCouponIds) {
        return couponRepository.findByIdIssueIdIn(issueCouponIds, LocalDate.now());
    }

    @Override
    public List<Coupon> findByIdItemIdIn(Long itemId) {
        return couponRepository.findByIdItemIdIn(itemId, LocalDate.now());
    }
}
