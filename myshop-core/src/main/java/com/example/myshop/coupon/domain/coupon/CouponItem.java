package com.example.myshop.coupon.domain.coupon;

import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon_items")
@ToString(callSuper = true)
@Entity
public class CouponItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_item_id")
    private Long id;

    private Long itemId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public CouponItem(Long itemId) {
        if (itemId == null) throw new InvalidParamException("CouponItem.itemId");
        this.itemId = itemId;
    }
}