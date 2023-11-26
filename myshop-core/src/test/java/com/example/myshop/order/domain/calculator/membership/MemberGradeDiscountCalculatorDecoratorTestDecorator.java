package com.example.myshop.order.domain.calculator.membership;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.member.domain.MemberGrade;
import com.example.myshop.order.infrastructure.calculator.membership.MemberGradeDiscountCalculatorDecorator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;

class MemberGradeDiscountCalculatorDecoratorTestDecorator {

    @ParameterizedTest(name = "[{index}] \"{0}\" => {2}")

    @MethodSource("memberGradeProvider")
    void memberGradeDiscountCalculator_test(MemberGrade grade, Money input, Money expected) {
        //given
        MemberGradeDiscountCalculatorDecorator sut = new MemberGradeDiscountCalculatorDecorator(grade);
        //when
        Money actual= sut.calculateDiscount(input);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> memberGradeProvider() {
        return Stream.of(
                Arguments.of(MemberGrade.BRONZE,  Money.valueOf(1000), Money.valueOf(900)),
                Arguments.of(MemberGrade.SILVER,  Money.valueOf(1000), Money.valueOf(800)),
                Arguments.of(MemberGrade.GOLD,  Money.valueOf(1000), Money.valueOf(700)),
                Arguments.of(MemberGrade.VIP,  Money.valueOf(1000), Money.valueOf(500)));
    }



}