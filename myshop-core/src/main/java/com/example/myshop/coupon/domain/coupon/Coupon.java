package com.example.myshop.coupon.domain.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "coupon_type")
@Table(name = "coupons")
@ToString
public abstract class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    protected String couponName;
    protected LocalDate expirationDate;
    protected Long adminId;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coupon", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("item_id asc")
    protected List<CouponItem> couponItems = new ArrayList<>();

    public abstract CouponType getCouponType();

    public abstract void changeCouponInfo(String couponName, Money discountAmount, Ratio discountRate, LocalDate expirationDate);

    public void clearCouponItems() {
        couponItems.clear();
    }

    public void addCouponItems(List<CouponItem> couponItems) {
        for (CouponItem couponItem : couponItems) {
            addCouponItem(couponItem);
        }
    }

    public void addCouponItem(CouponItem couponItem) {
        this.couponItems.add(couponItem);
        couponItem.setCoupon(this);
    }
}
