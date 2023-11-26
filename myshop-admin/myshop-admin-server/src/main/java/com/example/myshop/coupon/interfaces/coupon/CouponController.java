package com.example.myshop.coupon.interfaces.coupon;


import com.example.myshop.coupon.application.coupon.CouponService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.example.myshop.coupon.interfaces.coupon.CouponDto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/coupons")
public class CouponController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;

    @PostMapping
    public APIDataResponse<RegisterCouponResponse> registerCoupon(@RequestBody @Valid RegisterCouponRequest request) {
        var couponCommand = couponMapper.of(request);
        var couponId = couponService.registerCoupon(couponCommand);
        var response = couponMapper.of(couponId);
        return APIDataResponse.success(response);
    }

    @PutMapping("/{couponId}")
    public APIDataResponse<String> updateCoupon(@PathVariable Long couponId, @RequestBody @Valid UpdateCouponRequest request) {
        var couponCommand = couponMapper.of(request);
        couponService.updateCoupon(couponId, couponCommand);
        return APIDataResponse.success("ok");
    }

    @DeleteMapping("/{couponId}")
    public APIDataResponse<String> deleteReview(@PathVariable Long couponId) {
        couponService.deleteCoupon(couponId);
        return APIDataResponse.success("OK");
    }
}
