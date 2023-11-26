package com.example.myshop.coupon.interfaces.coupon;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RegisterCouponFormValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public  @interface RegisterCouponValidation {

    String message() default "Coupon is invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
