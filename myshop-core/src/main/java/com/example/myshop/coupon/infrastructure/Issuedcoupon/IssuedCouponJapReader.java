package com.example.myshop.coupon.infrastructure.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponReader;
import com.example.myshop.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class IssuedCouponJapReader implements IssuedCouponReader {

    private final IssuedCouponJpaRepository issuedCouponJpaRepository;

    @Override
    public IssuedCoupon getIssueCoupon(Long issueCouponId) {
        return issuedCouponJpaRepository.findById(issueCouponId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("IssueCoupon id not found %d", issueCouponId)));
    }

    @Override
    public List<IssuedCoupon> findByIdIn(List<Long> issueCouponIds) {
        return issuedCouponJpaRepository.findByIdIn(issueCouponIds);
    }

    @Override
    public Optional<IssuedCoupon> findById(Long issueCouponId) {
        return issuedCouponJpaRepository.findById(issueCouponId);
    }

    @Override
    public List<IssuedCoupon> findItemIdAndMemberId(Long itemId, Long memberId) {
        return issuedCouponJpaRepository.findByItemIdAndMemberId(itemId);
    }
}
