package com.example.myshop.coupon.application.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponInfo;
import com.example.myshop.coupon.domain.Issued.IssuedCouponReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class IssuedCouponQueryServiceImpl implements IssuedCouponQueryService {

    private final IssuedCouponReader issuedCouponReader;

    @Override
    public List<IssuedCouponInfo> retrieveIssue(Long itemId, Long memberId) {
        List<IssuedCoupon> issuedCoupons = issuedCouponReader.findItemIdAndMemberId(itemId, memberId);
        return issuedCoupons.stream().map(IssuedCouponInfo::fromEntity).toList();
    }
}
