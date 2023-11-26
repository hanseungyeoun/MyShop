package com.example.myshop.coupon.interfaces.Issuedcoupon;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssueDto {

    @Getter
    @Setter
    @ToString
    public static class CouponIssueRequest {
        @NotNull
        private String couponId;

        @NotNull
        private Long memberId;
    }

    @Getter
    @Setter
    @ToString
    public static class CouponIssueResponse {
        private String id;
    }
}
