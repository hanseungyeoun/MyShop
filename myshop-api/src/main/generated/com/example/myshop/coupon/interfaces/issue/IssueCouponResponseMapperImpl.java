package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.example.myshop.coupon.dto.IssueCouponInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class IssueCouponResponseMapperImpl implements IssueCouponResponseMapper {

    @Override
    public IssueCouponResponse of(IssueCouponInfo info) {
        if ( info == null ) {
            return null;
        }

        IssueCouponResponse.IssueCouponResponseBuilder issueCouponResponse = IssueCouponResponse.builder();

        issueCouponResponse.id( info.getId() );
        issueCouponResponse.couponId( info.getCouponId() );
        issueCouponResponse.memberId( info.getMemberId() );
        issueCouponResponse.expirationDate( info.getExpirationDate() );
        issueCouponResponse.orderId( info.getOrderId() );
        issueCouponResponse.issueCouponType( info.getIssueCouponType() );
        issueCouponResponse.issueCouponTypeDescription( info.getIssueCouponTypeDescription() );

        return issueCouponResponse.build();
    }
}
