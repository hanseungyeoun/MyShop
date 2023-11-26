package com.example.myshop.coupon.infrastructure.coupon;

import com.example.myshop.CoreTestConfiguration;
import com.example.myshop.InMemoryDBJpaTest;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.RateCoupon;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.infrastructure.item.ItemJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.example.myshop.CoreFixtures.aRateCoupon;
import static com.example.myshop.CoreFixtures.anItem;

@Transactional
@InMemoryDBJpaTest
@ContextConfiguration(classes = {CoreTestConfiguration.class})
class CouponJpaRepositoryTest {
    @Autowired
    private CouponJpaRepository couponJpaRepository;

    @Autowired
    private ItemJpaRepository itemJpaRepository;

    @Test
    void findByIdItemIdIn_test() {
        //given
        Item item = itemJpaRepository.save(anItem().build());
        RateCoupon coupon = aRateCoupon().itemIds(List.of(item.getId())).build();
        couponJpaRepository.save((Coupon) coupon);

        //when
        List<Coupon> coupons = couponJpaRepository.findByIdItemIdIn(item.getId(), LocalDate.now());

        //then
        Assertions.assertThat(coupons).hasSize(1);
    }
}