package com.example.myshop.coupon.domain.Issued;


import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Table(name = "issued_coupons")
@Entity
public class IssuedCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "issuse_id")
    private Long id;

    private Long couponId;
    private Long memberId;
    private LocalDate expirationDate;
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private IssuedCouponStatus status;

    //==생성 메서드==//
    @Builder
    public IssuedCoupon(Long couponId, Long memberId, LocalDate expirationDate) {
        if (couponId == null) throw new InvalidParamException("Issuecoupon.couponId is null");
        if (memberId == null) throw new InvalidParamException("IssueCoupon.memberId is null");
        if (expirationDate == null) throw new InvalidParamException("IssueCoupon.expirationDate is null");

        this.couponId = couponId;
        this.memberId = memberId;
        this.expirationDate = expirationDate;
        this.status = IssuedCouponStatus.NOT_USED;
    }

    //==비즈니스 로직==//
    public void apply(Long orderId) {
        verifyCouponAvailable();
        this.orderId = orderId;
        this.status = IssuedCouponStatus.USED;
    }

    public void reset(Long orderId) {
        verifyNotExpiration();
        this.orderId = null;
        this.status = IssuedCouponStatus.NOT_USED;
    }

    public void setExpired() {
        verifyNotUsed();
        this.status = IssuedCouponStatus.EXPIRED;
    }

    public void verifyCouponAvailable() {
        verifyNotExpiration();
        verifyNotUsed();
    }

    private void verifyNotUsed() {
        if (this.status == IssuedCouponStatus.USED) {
            throw new IllegalStatusException("이미 사용한 쿠폰입니다.");
        }
    }

    private void verifyNotExpiration() {
        if (this.status == IssuedCouponStatus.EXPIRED) {
            throw new IllegalStatusException("사용 기간이 만료된 쿠폰입니다.");
        }

        if (LocalDate.now().isAfter(getExpirationDate())) {
            throw new IllegalStatusException("사용 기간이 만료된 쿠폰입니다.");
        }
    }
    //==조회 로직==//

    public boolean isExpiration() {
        return LocalDate.now().isAfter(expirationDate);
    }
}
