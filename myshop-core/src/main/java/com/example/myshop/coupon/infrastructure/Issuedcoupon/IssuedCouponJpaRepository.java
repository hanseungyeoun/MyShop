package com.example.myshop.coupon.infrastructure.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssuedCouponJpaRepository extends JpaRepository<IssuedCoupon, Long>  {

    @Query("select i " +
            "   from IssuedCoupon i " +
            "   join Coupon c on c.id = i.couponId " +
            "   join CouponItem ci on ci.coupon.id = c.id " +
            "   where " +
            "       ci.itemId = :itemId and " +
            "       i.memberId = :memberId and " +
            "       i.status = com.example.myshop.coupon.domain.Issued.IssuedCouponStatus.NOT_USED")
    List<IssuedCoupon> findByItemIdAndMemberId(@Param("memberId") Long memberId);
    List<IssuedCoupon> findByIdIn(List<Long> issueCouponIds);
}