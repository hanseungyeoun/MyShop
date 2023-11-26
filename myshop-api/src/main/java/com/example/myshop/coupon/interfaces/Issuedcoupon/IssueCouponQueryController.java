package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.example.myshop.coupon.application.Issuedcoupon.IssuedCouponQueryService;
import com.example.myshop.member.interfaces.MemberPrincipal;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/issue")
public class IssueCouponQueryController {

    private final IssuedCouponQueryService issuedCouponQueryService;
    private final IssuedCouponResponseMapper mapper;

    @GetMapping("/me")
    public APIDataResponse<List<IssuedCouponResponse>> retrieveIssueCoupons(Long itemId,
            @AuthenticationPrincipal MemberPrincipal authentication
    ) {
        var results = issuedCouponQueryService.retrieveIssue(itemId, authentication.getId());
        var response = results.stream().map(mapper::of).toList();
        return APIDataResponse.success(response);
    }
}
