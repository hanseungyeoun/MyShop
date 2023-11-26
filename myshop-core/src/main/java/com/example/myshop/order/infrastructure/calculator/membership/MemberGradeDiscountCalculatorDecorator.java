package com.example.myshop.order.infrastructure.calculator.membership;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.member.domain.MemberGrade;
import com.example.myshop.order.infrastructure.calculator.DiscountCalculatorDecorator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberGradeDiscountCalculatorDecorator implements DiscountCalculatorDecorator {

    private final MemberGrade memberGrade;

    public Money calculateDiscount(Money totalAmount) {
        return totalAmount.minus(totalAmount.times(memberGrade.getDiscountRate()));
    }
}
