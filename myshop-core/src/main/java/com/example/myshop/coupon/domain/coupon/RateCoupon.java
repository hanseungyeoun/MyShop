package com.example.myshop.coupon.domain.coupon;


import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.common.jpa.RatioConverter;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("RATE")
@NoArgsConstructor
public class RateCoupon extends Coupon {

    @Embedded
    @AttributeOverride(name = "rate", column = @Column(name = "discount_rate"))
    @Convert(converter = RatioConverter.class, attributeName = "discount_rate")
    private Ratio discountRate = Ratio.ZERO;

    @Override
    public CouponType getCouponType() {
        return CouponType.FIXED_RATE;
    }

    //==생성 메서드==//
    @Builder(builderClassName = "RateCouponBuilder", builderMethodName = "RateCouponBuilder")
    public RateCoupon(String couponName, Ratio discountRate, LocalDate expirationDate, List<Long> itemIds){
        if (!StringUtils.hasText(couponName)) throw new InvalidParamException("Coupon.couponName");
        if (discountRate == null) throw new InvalidParamException("Coupon.discountRate");
        if (expirationDate == null) throw new InvalidParamException("Coupon.expirationDate");
     //   if (adminId == null) throw new InvalidParamException("Coupon.adminId");

        if(itemIds != null) {
            List<CouponItem> couponItems = itemIds.stream().map(CouponItem::new).toList();
            addCouponItems(couponItems);
        }

        this.couponName = couponName;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
    }

    @Override
    public void changeCouponInfo(String couponName, Money discountAmount, Ratio discountRate, LocalDate expirationDate) {
        this.couponName = couponName;
        this.discountRate = discountRate;
        this.expirationDate = expirationDate;
    }
}
