package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.coupon.domain.coupon.CouponInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponResponseMapper {

    CouponResponse of(CouponInfo couponInfo);
}
