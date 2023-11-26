package com.example.myshop.coupon.application.Issuedcoupon;

import com.example.myshop.coupon.domain.coupon.Coupon;
import com.example.myshop.coupon.domain.coupon.CouponReader;
import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponStore;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponIssueServiceImpl implements CouponIssueService {

    private final IssuedCouponStore issuedCouponStore;
    private final MemberReader memberReader;
    private final CouponReader couponReader;

    @Override
    public Long CouponIssueCoupon(CouponIssueCommand.IssueCouponCommand command) {
        memberValidation(command.getMemberId());
        Coupon coupon = couponReader.getCoupon(command.getCouponId());
        IssuedCoupon initIssue = command.toEntity(coupon.getExpirationDate());
        IssuedCoupon storedIssue = issuedCouponStore.store(initIssue);
        return storedIssue.getId();
    }

    private void memberValidation(Long memberId) {
        Optional<Member> optionalMember = memberReader.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException(String.format("member id not found %d", memberId));
        }
    }
}
