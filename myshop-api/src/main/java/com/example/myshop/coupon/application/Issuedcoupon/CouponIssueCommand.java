package com.example.myshop.coupon.application.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssueCommand {

    @Getter
    @Builder
    @ToString
    public static class IssueCouponCommand {
        private final Long couponId;
        private final Long memberId;

        public IssuedCoupon toEntity(LocalDate expirationDate) {
            return IssuedCoupon.builder()
                    .couponId(couponId)
                    .memberId(memberId)
                    .expirationDate(expirationDate)
                    .build();
        }
    }
}
