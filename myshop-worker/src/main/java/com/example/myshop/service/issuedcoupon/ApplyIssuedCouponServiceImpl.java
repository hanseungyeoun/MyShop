package com.example.myshop.service.issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponReader;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class ApplyIssuedCouponServiceImpl implements ApplyIssuedCouponService {

    private final IssuedCouponReader issuedCouponReader;
    private final OrderReader orderReader;

    @Override
    public void applyIssuedCouponsByOrderId(Long orderId) {
        Order order = orderReader.getOrder(orderId);
        List<Long> issueCouponIds = order.getIssueCoupons();
        if (issueCouponIds.size() == 0) return;

        applyCoupons(orderId, issueCouponIds);
    }

    @Override
    public void resetIssuedCouponsByOrderId(Long orderId) {
        Order order = orderReader.getOrder(orderId);
        List<Long> issueCouponIds = order.getIssueCoupons();
        if (issueCouponIds.size() == 0) return;

        resetCoupons(orderId, issueCouponIds);
    }

    private void applyCoupons(Long orderId, List<Long> issueCouponIds) {
        List<IssuedCoupon> issuedCoupons = issuedCouponReader.findByIdIn(issueCouponIds);

        for (IssuedCoupon issuedCoupon : issuedCoupons) {
            applyCoupon(orderId, issuedCoupon);
        }
    }

    private void resetCoupons(Long orderId, List<Long> issueCouponIds) {
        List<IssuedCoupon> issuedCoupons = issuedCouponReader.findByIdIn(issueCouponIds);

        for (IssuedCoupon issuedCoupon : issuedCoupons) {
            resetCoupon(orderId, issuedCoupon);
        }
    }

    private void applyCoupon(Long orderId, IssuedCoupon issuedCoupon) {
        issuedCoupon.apply(orderId);
    }

    private void resetCoupon(Long orderId, IssuedCoupon issuedCoupon) {
        issuedCoupon.reset(orderId);
    }

    private void orderValidation(Long orderId) {
        Optional<Order> optionalOrder = orderReader.findById(orderId);
        if(optionalOrder.isEmpty()) {
           throw new EntityNotFoundException(String.format("order id not found %d", orderId));
        }
    }
}
