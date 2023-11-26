package com.example.myshop.order.domain.calculator.coupon;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.IntegrationTest;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.order.infrastructure.calculator.coupon.CouponCalculator;
import com.example.myshop.order.infrastructure.calculator.coupon.CouponDiscountCalculatorDecorator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.stream.Stream;

import static com.example.myshop.CoreFixtures.*;

@IntegrationTest
@SpringBootTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class CouponDiscountCalculatorDecoratorTest {

    @Autowired
    List<CouponCalculator> calculators;

    @ParameterizedTest(name = "[{index}] \"{0}, {1}\" => {2}")
    @MethodSource("memberCouponProvider")
    void calculateDiscount_test(Money totalAmount, List<Coupon> coupons,  Money expected) {
        //when
        CouponDiscountCalculatorDecorator sut = new CouponDiscountCalculatorDecorator(calculators, coupons);

        //then
        Money actual= sut.calculateDiscount(totalAmount);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> memberCouponProvider() {
        return Stream.of(
                Arguments.of(Money.valueOf(10000), List.of(aRateCoupon().discountRate(Ratio.valueOf(0.1)).build()), Money.valueOf(9000)),
                Arguments.of(Money.valueOf(10000), List.of(anAmountCoupon().discountAmount(Money.valueOf(1000)).build()), Money.valueOf(9000)));
    }
}