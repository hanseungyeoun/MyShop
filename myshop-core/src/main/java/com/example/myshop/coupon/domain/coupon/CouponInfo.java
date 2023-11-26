package com.example.myshop.coupon.domain.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.exception.InvalidParamException;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class CouponInfo {
    private final Long id;
    private final String couponName;
    private final Integer discountAmount;
    private final Double discountRate;
    private final LocalDate expirationDate;
    private final String couponType;
    private final String couponTypeDescription;

    public CouponInfo(
            Long id,
            String couponName,
            Money discountAmount,
            Ratio discountRate,
            LocalDate expirationDate,
            CouponType couponType
    ) {
        this.id = id;
        this.couponName = couponName;
        this.discountAmount = discountAmount.getMoney().intValue();
        this.discountRate = discountRate.getRate();
        this.expirationDate = expirationDate;
        this.couponType = couponType.name();
        this.couponTypeDescription = couponType.getDescription();
    }

    public static CouponInfo fromEntity(Coupon coupon) {
        if (coupon.getCouponType() == CouponType.FIXED_AMOUNT) {
            AmountCoupon amountCoupon = (AmountCoupon) coupon;
            return fromEntity(amountCoupon);
        } else if (coupon.getCouponType() == CouponType.FIXED_RATE) {
            RateCoupon rateCoupon = (RateCoupon) coupon;
            return fromEntity(rateCoupon);
        } else {
            throw new InvalidParamException("coupon type error");
        }
    }

    private static CouponInfo fromEntity(AmountCoupon amountCoupon) {
        return new CouponInfo(
                            amountCoupon.getId(),
                            amountCoupon.getCouponName(),
                            amountCoupon.getDiscountAmount(),
                            Ratio.ZERO,
                            amountCoupon.getExpirationDate(),
                            amountCoupon.getCouponType());
    }

    private static CouponInfo fromEntity(RateCoupon rateCoupon) {
        return new CouponInfo(
                rateCoupon.getId(),
                rateCoupon.getCouponName(),
                Money.ZERO,
                rateCoupon.getDiscountRate(),
                rateCoupon.getExpirationDate(),
                rateCoupon.getCouponType());
    }
}
