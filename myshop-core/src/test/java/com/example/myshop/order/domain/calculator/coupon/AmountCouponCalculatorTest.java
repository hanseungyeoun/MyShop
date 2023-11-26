package com.example.myshop.order.domain.calculator.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.coupon.domain.coupon.AmountCoupon;
import com.example.myshop.order.infrastructure.calculator.coupon.AmountCouponCalculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.example.myshop.CoreFixtures.anAmountCoupon;


class AmountCouponCalculatorTest {

    private AmountCouponCalculator sut;

    @BeforeEach
    void beforeEach() {
        sut = new AmountCouponCalculator();
    }


    @Test
    void amountCouponCalculator_test() {
        //given
        Money input = Money.valueOf(10000);
        AmountCoupon coupon = anAmountCoupon()
                .discountAmount(Money.valueOf(1000))
                .build();
        //when
        Money actual= sut.calculation(input, coupon);

        //then
        Assertions.assertThat(actual).isEqualTo(Money.valueOf(1000));
    }
}