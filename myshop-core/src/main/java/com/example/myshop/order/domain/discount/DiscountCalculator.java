package com.example.myshop.order.domain.discount;

import com.example.myshop.common.jpa.Money;

import java.util.List;

public interface DiscountCalculator {

    Money calculateDiscountAmounts(Money totalAmount, List<Long> issueCouponIds, Long memberId);
}
