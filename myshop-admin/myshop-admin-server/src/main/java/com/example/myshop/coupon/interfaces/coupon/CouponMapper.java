package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.Ratio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import static com.example.myshop.coupon.application.coupon.CouponCommand.*;
import static com.example.myshop.coupon.application.coupon.CouponCommand.RegisterCouponCommand;
import static com.example.myshop.coupon.interfaces.coupon.CouponDto.*;
import static com.example.myshop.coupon.interfaces.coupon.CouponDto.RegisterCouponRequest;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CouponMapper {

    @Mapping(source = "discountAmount", target = "discountAmount", qualifiedByName = "integerToMoney")
    @Mapping(source = "discountRate", target = "discountRate", qualifiedByName = "doubleToRate")
    RegisterCouponCommand of(RegisterCouponRequest request);

    RegisterCouponResponse of(Long id);

    @Mapping(source = "discountAmount", target = "discountAmount", qualifiedByName = "integerToMoney")
    @Mapping(source = "discountRate", target = "discountRate", qualifiedByName = "doubleToRate")
    UpdateCouponCommand of(UpdateCouponRequest request);

    @Named("integerToMoney")
    static Money integerToMoney(Integer value) {
        return value == null ? null : Money.valueOf(value);
    }

    @Named("doubleToRate")
    static Ratio typeToRate(Double value) {
        return value == null ? null : Ratio.valueOf(value.doubleValue());
    }
}
