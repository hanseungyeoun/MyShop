package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.coupon.application.coupon.CouponCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:30:32+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class CouponMapperImpl implements CouponMapper {

    @Override
    public CouponCommand.RegisterCouponCommand of(CouponDto.RegisterCouponRequest request) {
        if ( request == null ) {
            return null;
        }

        CouponCommand.RegisterCouponCommand.RegisterCouponCommandBuilder registerCouponCommand = CouponCommand.RegisterCouponCommand.builder();

        registerCouponCommand.discountAmount( CouponMapper.integerToMoney( request.getDiscountAmount() ) );
        registerCouponCommand.discountRate( CouponMapper.typeToRate( request.getDiscountRate() ) );
        registerCouponCommand.couponName( request.getCouponName() );
        registerCouponCommand.expirationDate( request.getExpirationDate() );
        Set<Long> set = request.getItemIds();
        if ( set != null ) {
            registerCouponCommand.itemIds( new ArrayList<Long>( set ) );
        }
        registerCouponCommand.couponType( request.getCouponType() );

        return registerCouponCommand.build();
    }

    @Override
    public CouponDto.RegisterCouponResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        CouponDto.RegisterCouponResponse registerCouponResponse = new CouponDto.RegisterCouponResponse();

        if ( id != null ) {
            registerCouponResponse.setId( String.valueOf( id ) );
        }

        return registerCouponResponse;
    }

    @Override
    public CouponCommand.UpdateCouponCommand of(CouponDto.UpdateCouponRequest request) {
        if ( request == null ) {
            return null;
        }

        CouponCommand.UpdateCouponCommand.UpdateCouponCommandBuilder updateCouponCommand = CouponCommand.UpdateCouponCommand.builder();

        updateCouponCommand.discountAmount( CouponMapper.integerToMoney( request.getDiscountAmount() ) );
        updateCouponCommand.discountRate( CouponMapper.typeToRate( request.getDiscountRate() ) );
        updateCouponCommand.couponName( request.getCouponName() );
        updateCouponCommand.expirationDate( request.getExpirationDate() );
        List<Long> list = request.getItemIds();
        if ( list != null ) {
            updateCouponCommand.itemIds( new ArrayList<Long>( list ) );
        }
        updateCouponCommand.couponType( request.getCouponType() );

        return updateCouponCommand.build();
    }
}
