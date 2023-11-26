package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.example.myshop.coupon.domain.Issued.IssuedCouponInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IssuedCouponResponseMapper {

    IssuedCouponResponse of(IssuedCouponInfo info);
}
