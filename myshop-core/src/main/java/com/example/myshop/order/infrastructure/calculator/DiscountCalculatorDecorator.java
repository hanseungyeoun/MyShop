package com.example.myshop.order.infrastructure.calculator;

import com.example.myshop.common.jpa.Money;

@FunctionalInterface
public interface DiscountCalculatorDecorator {

    Money calculateDiscount(Money price);

    default DiscountCalculatorDecorator andThen(DiscountCalculatorDecorator next) {
        return price -> next.calculateDiscount(calculateDiscount(price));
    }
}
