package com.example.myshop.coupon.domain.Issued;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@ToString
public class IssuedCouponInfo {
    private final Long id;
    private final Long couponId;
    private final Long memberId;
    private final LocalDate expirationDate;
    private final Long orderId;
    private final String issueCouponType;
    private final String issueCouponTypeDescription;

    public static IssuedCouponInfo fromEntity(IssuedCoupon issuedCoupon) {
        return IssuedCouponInfo.builder()
                .id( issuedCoupon.getId())
                .couponId( issuedCoupon.getCouponId())
                .memberId( issuedCoupon.getMemberId())
                .expirationDate( issuedCoupon.getExpirationDate())
                .orderId( issuedCoupon.getOrderId())
                .issueCouponType( issuedCoupon.getStatus().name())
                .issueCouponTypeDescription( issuedCoupon.getStatus().getDescription())
                .build();
    }
}
