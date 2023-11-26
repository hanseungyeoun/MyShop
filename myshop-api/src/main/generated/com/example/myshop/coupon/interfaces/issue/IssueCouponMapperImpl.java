package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.example.myshop.coupon.application.Issuedcoupon.IssueCommand;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:20+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class IssueCouponMapperImpl implements IssueCouponMapper {

    @Override
    public IssueCommand.IssueCouponCommand of(IssueCouponDto.IssueCouponRequest request) {
        if ( request == null ) {
            return null;
        }

        IssueCommand.IssueCouponCommand.IssueCouponCommandBuilder issueCouponCommand = IssueCommand.IssueCouponCommand.builder();

        if ( request.getCouponId() != null ) {
            issueCouponCommand.couponId( Long.parseLong( request.getCouponId() ) );
        }
        issueCouponCommand.memberId( request.getMemberId() );

        return issueCouponCommand.build();
    }

    @Override
    public IssueCouponDto.RegisterIssueCouponResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        IssueCouponDto.RegisterIssueCouponResponse registerIssueCouponResponse = new IssueCouponDto.RegisterIssueCouponResponse();

        if ( id != null ) {
            registerIssueCouponResponse.setId( String.valueOf( id ) );
        }

        return registerIssueCouponResponse;
    }
}
