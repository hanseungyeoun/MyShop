package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.coupon.dto.CouponInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CouponResponseMapperImpl implements CouponResponseMapper {

    @Override
    public CouponResponse of(CouponInfo couponInfo) {
        if ( couponInfo == null ) {
            return null;
        }

        CouponResponse.CouponResponseBuilder couponResponse = CouponResponse.builder();

        couponResponse.id( couponInfo.getId() );
        couponResponse.couponName( couponInfo.getCouponName() );
        couponResponse.discountAmount( couponInfo.getDiscountAmount() );
        couponResponse.discountRate( couponInfo.getDiscountRate() );
        couponResponse.expirationDate( couponInfo.getExpirationDate() );
        couponResponse.couponType( couponInfo.getCouponType() );
        couponResponse.couponTypeDescription( couponInfo.getCouponTypeDescription() );

        return couponResponse.build();
    }
}
