package com.example.myshop.order.domain.calculator.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.coupon.domain.coupon.RateCoupon;
import com.example.myshop.order.infrastructure.calculator.coupon.RateCouponCalculator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.example.myshop.CoreFixtures.*;


class RateCouponCalculatorTest {

    private RateCouponCalculator sut;

    @BeforeEach
    void beforeEach() {
        sut = new RateCouponCalculator();
    }

    @Test
    void amountCouponCalculator_test() {
        //given
        Money input = Money.valueOf(10000);
        RateCoupon coupon = aRateCoupon()
                .discountRate(Ratio.valueOf(0.1))
                .build();

        //when
        Money actual= sut.calculation(input, coupon);

        //then
        Assertions.assertThat(actual).isEqualTo(Money.valueOf(1000));
    }

}