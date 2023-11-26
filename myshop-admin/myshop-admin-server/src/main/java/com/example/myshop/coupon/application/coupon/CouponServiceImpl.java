package com.example.myshop.coupon.application.coupon;
import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponItem;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import com.example.myshop.coupon.domain.coupon.CouponStore;
import com.example.myshop.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.myshop.coupon.application.coupon.CouponCommand.*;


@RequiredArgsConstructor
@Transactional
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponReader couponReader;
    private final CouponStore couponStore;

    @Override
    public Long registerCoupon(RegisterCouponCommand command) {
        Coupon storedCoupon = couponStore.store(command.toEntity());
        return storedCoupon.getId();
    }

    @Override
    public void updateCoupon(Long couponId, UpdateCouponCommand command) {
        Coupon coupon = couponReader.getCoupon(couponId);

        if(coupon.getCouponType() != command.getCouponType())
            throw new InvalidParamException("쿠폰 타입은 변경할 수 없습니다");

        coupon.changeCouponInfo(command.getCouponName(), command.getDiscountAmount(), command.getDiscountRate(),  command.getExpirationDate());
        coupon.clearCouponItems();

        List<CouponItem> couponItems = command.getItemIds().stream().map(CouponItem::new).toList();
        coupon.addCouponItems(couponItems);
    }

    @Override
    public void deleteCoupon(Long couponId) {
        couponStore.delete(couponId);
    }

}
