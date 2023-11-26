package com.example.myshop.order.domain.calculator;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.IntegrationTest;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Password;
import com.example.myshop.common.jpa.RoleType;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberGrade;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.order.domain.discount.DiscountCalculator;
import com.example.myshop.order.infrastructure.calculator.coupon.CouponCalculator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static com.example.myshop.CoreFixtures.*;

@SpringBootTest
@IntegrationTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class DiscountCalculatorImplTest {

    @Autowired
    private DiscountCalculator sut;

    @MockBean
    MemberReader memberReader;

    @MockBean
    CouponReader couponReader;

    @Autowired
    List<CouponCalculator> calculators;

    @Test
    void 쿠폰이_없을때_할인_금액계산() {
        //given
        long memberId = 1L;
        Money totalAmount = Money.valueOf(10000);

        Member member = Member.builder()
                            .username("회원")
                            .password(new Password(""))
                            .role(RoleType.ROLE_USER)
                             .build();

        member.changeMemberGrade(MemberGrade.BRONZE);

        given(couponReader.findByIdIssueIdIn(List.of())).willReturn(List.of());
        given(memberReader.getMember(memberId)).willReturn(member);

        //when
        Money actual = sut.calculateDiscountAmounts(totalAmount, List.of(), memberId);

        //then
        assertThat(actual).isEqualTo(Money.valueOf(9000));
    }

    @Test
    void 쿠폰이_정율할인_금액계산() {
        //given
        long memberId = 1L;
        Money totalAmount = Money.valueOf(10000);
        Member member = aMember().build();
        member.changeMemberGrade(MemberGrade.BRONZE);

        given(couponReader.findByIdIssueIdIn(any())).willReturn(List.of(aRateCoupon().build()));
        given(memberReader.getMember(memberId)).willReturn(member);

        //when
        Money actual = sut.calculateDiscountAmounts(totalAmount, List.of(), memberId);

        //then
        assertThat(actual).isEqualTo(Money.valueOf(8100));
    }

    @Test
    void 쿠폰이_정율_할인_금액계산() {
        //given
        long memberId = 1L;
        Money totalAmount = Money.valueOf(10000);

        given(couponReader.findByIdIssueIdIn(any())).willReturn(List.of(aRateCoupon().build()));
        given(memberReader.getMember(memberId)).willReturn(aMember().build());

        //when
        Money actual = sut.calculateDiscountAmounts(totalAmount, List.of(), memberId);

        //then
        assertThat(actual).isEqualTo(Money.valueOf(8100));
    }

    @Test
    void 쿠폰이_정액_할인_금액계산() {
        //given
        long memberId = 1L;
        Money totalAmount = Money.valueOf(10000);

        given(couponReader.findByIdIssueIdIn(any())).willReturn(List.of(anAmountCoupon().build()));
        given(memberReader.getMember(memberId)).willReturn(aMember().build());

        //when
        Money actual = sut.calculateDiscountAmounts(totalAmount, List.of(), memberId);

        //then
        assertThat(actual).isEqualTo(Money.valueOf(8100));
    }
}