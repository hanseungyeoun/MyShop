package com.example.myshop.coupon.interfaces.Issuedcoupon;


import com.example.myshop.coupon.application.Issuedcoupon.CouponIssueCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CouponIssueMapper {

    CouponIssueCommand.IssueCouponCommand of(CouponIssueDto.CouponIssueRequest request);

    CouponIssueDto.CouponIssueResponse of(Long id);
}
