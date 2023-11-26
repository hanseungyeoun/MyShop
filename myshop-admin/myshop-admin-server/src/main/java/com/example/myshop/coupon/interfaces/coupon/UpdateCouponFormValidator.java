package com.example.myshop.coupon.interfaces.coupon;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import static com.example.myshop.coupon.domain.coupon.CouponType.FIXED_AMOUNT;
import static com.example.myshop.coupon.domain.coupon.CouponType.FIXED_RATE;

public class UpdateCouponFormValidator implements ConstraintValidator<UpdateCouponValidation, CouponDto.UpdateCouponRequest> {

    @Override
    public boolean isValid(CouponDto.UpdateCouponRequest request, ConstraintValidatorContext context) {
        int invalidCount = 0;

        if (request.getCouponType() == null ) {
            addConstraintViolation(context, "쿠폰 타입은 필수 입니다. .", "couponType");
            invalidCount += 1;
        }

        if (!StringUtils.hasText(request.getCouponName())) {
            addConstraintViolation(context, "쿠폰 이름은 필수 입니다. .", "couponName");
            invalidCount += 1;
        }

        if (request.getExpirationDate() == null ) {
            addConstraintViolation(context, "쿠폰 타입은 필수 입니다. .", "expirationDate");
            invalidCount += 1;
        }


        if(request.getCouponType() == FIXED_AMOUNT && request.getDiscountAmount() == null){
            addConstraintViolation(context, "할인 가는  필수 입니다. .", "discountRate");
            invalidCount += 1;
        }

        if(request.getCouponType() == FIXED_RATE && request.getDiscountRate() == null){
            addConstraintViolation(context, "할인률은  필수 입니다. .", "discountRate");
            invalidCount += 1;
        }

        if (request.getItemIds() == null) {
            addConstraintViolation(context, "상품 목록은 필수 입니다. .", "discountRate");
            invalidCount += 1;
        }

        return invalidCount == 0;
    }

    private void addConstraintViolation(
            final ConstraintValidatorContext context,
            final String errorMessage,
            final String firstNode,
            final String secondNode,
            final String thirdNode
    ) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(firstNode)
                .addPropertyNode(secondNode)
                .addPropertyNode(thirdNode)
                .addConstraintViolation();
    }

    private void addConstraintViolation(
            final ConstraintValidatorContext context,
            final String errorMessage,
            final String firstNode
    ) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(firstNode)
                .addConstraintViolation();
    }

    private void addConstraintViolation(
            final ConstraintValidatorContext context,
            final String errorMessage,
            final String firstNode,
            final String secondNode
    ) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(errorMessage)
                .addPropertyNode(firstNode)
                .addPropertyNode(secondNode)
                .addConstraintViolation();
    }
}
