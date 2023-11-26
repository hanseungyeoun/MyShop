package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.coupon.domain.coupon.CouponType;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponDto {

    @Getter
    @Setter
    @ToString
    @RegisterCouponValidation
    public static class RegisterCouponRequest {
        private String couponName;

        private Integer discountAmount;

        private Double discountRate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;

        private Set<Long> itemIds;

        private CouponType couponType;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterCouponResponse {
        private String id;
    }

    @Getter
    @ToString
    @UpdateCouponValidation
    public static class UpdateCouponRequest {
        private String couponName;
        private Integer discountAmount;
        private Double discountRate;

        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate expirationDate;

        private List<Long> itemIds;

        private CouponType couponType;
    }
}
