package com.example.myshop.coupon.domain.coupon;


import com.example.myshop.common.jpa.MoneyConverter;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("AMOUNT")
@NoArgsConstructor
@ToString(callSuper = true)
public class AmountCoupon extends Coupon {

    @Embedded
    @AttributeOverride(name = "money", column = @Column(name = "discount_amount"))
    @Convert(converter = MoneyConverter.class, attributeName = "discount_amount")
    private Money discountAmount = Money.ZERO;

    //==생성 메서드==//
    @Builder(builderClassName = "AmountCouponBuilder", builderMethodName = "AmountCouponBuilder")
    public AmountCoupon(String couponName, Money discountAmount, LocalDate expirationDate, List<Long> itemIds) {
        if (!StringUtils.hasText(couponName)) throw new InvalidParamException("Coupon.couponName");
        if (discountAmount == null) throw new InvalidParamException("Coupon.discountRate");
        if (expirationDate == null) throw new InvalidParamException("Coupon.expirationDate");
     //   if (adminId == null) throw new InvalidParamException("Coupon.adminId");

        if (itemIds != null) {
            List<CouponItem> couponItems = itemIds.stream().map(CouponItem::new).toList();
            addCouponItems(couponItems);
        }

        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }

    @Override
    public CouponType getCouponType() {
        return CouponType.FIXED_AMOUNT;
    }

    @Override
    public void changeCouponInfo(String couponName, Money discountAmount, Ratio discountRate, LocalDate expirationDate) {
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.expirationDate = expirationDate;
    }
}
