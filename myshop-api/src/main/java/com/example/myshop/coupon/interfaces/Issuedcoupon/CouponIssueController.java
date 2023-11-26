package com.example.myshop.coupon.interfaces.Issuedcoupon;

import com.example.myshop.coupon.application.Issuedcoupon.CouponIssueService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/issue")
public class CouponIssueController {

    private final CouponIssueService issueCouponService;
    private final CouponIssueMapper issueMapper;

    @PostMapping
    public APIDataResponse<CouponIssueDto.CouponIssueResponse> registerCoupon(@RequestBody @Valid CouponIssueDto.CouponIssueRequest request) {
        var command = issueMapper.of(request);
        var issueId = issueCouponService.CouponIssueCoupon(command);
        var response = issueMapper.of(issueId);
        return APIDataResponse.success(response);
    }
}
