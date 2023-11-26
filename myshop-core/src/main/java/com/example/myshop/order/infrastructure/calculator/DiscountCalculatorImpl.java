package com.example.myshop.order.infrastructure.calculator;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import com.example.myshop.member.domain.MemberGrade;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.order.domain.discount.DiscountCalculator;
import com.example.myshop.order.infrastructure.calculator.coupon.CouponCalculator;
import com.example.myshop.order.infrastructure.calculator.coupon.CouponDiscountCalculatorDecorator;
import com.example.myshop.order.infrastructure.calculator.membership.MemberGradeDiscountCalculatorDecorator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class DiscountCalculatorImpl implements DiscountCalculator {

    private final MemberReader memberReader;
    private final CouponReader couponReader;
    private final List<CouponCalculator> calculators;

    public Money calculateDiscountAmounts(Money totalAmount, List<Long> issueCouponIds, Long memberId) {
        List<Coupon> coupons = couponReader.findByIdIssueIdIn(issueCouponIds);
        MemberGrade memberGrade = memberReader.getMember(memberId).getMemberGrade();
        DiscountCalculatorDecorator memberCalculator = new MemberGradeDiscountCalculatorDecorator(memberGrade);
        DiscountCalculatorDecorator couponDiscountCalculator = new CouponDiscountCalculatorDecorator(calculators, coupons);
        DiscountCalculatorDecorator calculator = couponDiscountCalculator.andThen(memberCalculator);

        return calculator.calculateDiscount(totalAmount);
    }
}
