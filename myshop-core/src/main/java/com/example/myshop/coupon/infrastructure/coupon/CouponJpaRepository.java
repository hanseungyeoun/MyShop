package com.example.myshop.coupon.infrastructure.coupon;

import com.example.myshop.coupon.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CouponJpaRepository extends JpaRepository<Coupon, Long> {

    @Query("select c from Coupon c join IssuedCoupon ic on c.id = ic.couponId where ic.couponId in :couponIds  and c.expirationDate >= :localDate")
    List<Coupon> findByIdIssueIdIn(@Param(value = "couponIds") List<Long> couponIds,  @Param(value = "localDate") LocalDate localDate);

    @Query("select c from Coupon c join c.couponItems ci where ci.itemId = :itemId  and c.expirationDate >= :localDate")
    List<Coupon> findByIdItemIdIn(@Param(value = "itemId") Long itemId, @Param(value = "localDate") LocalDate localDate);
}