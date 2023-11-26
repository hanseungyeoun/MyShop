package com.example.myshop.coupon.interfaces.coupon;

import com.example.myshop.coupon.application.coupon.CouponQueryService;
import com.example.myshop.member.interfaces.MemberPrincipal;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponQueryController {

    private final CouponQueryService service;
    private final CouponResponseMapper mapper;

    @GetMapping("")
    public APIDataResponse<List<CouponResponse>> retrieveCouponsByItemId(
            @RequestParam(value = "itemId") Long itemId,
            @AuthenticationPrincipal MemberPrincipal authentication
    ) {
        var results = service.retrieveCouponByItemId(itemId);
        var response = results.stream().map(mapper::of).toList();
        return APIDataResponse.success(response);
    }

    @GetMapping("/{couponId}")
    public APIDataResponse<CouponResponse> retrieveCoupon(@PathVariable Long couponId) {
        var result = service.retrieveCoupon(couponId);
        var response = mapper.of(result);
        return APIDataResponse.success(response);
    }
}
