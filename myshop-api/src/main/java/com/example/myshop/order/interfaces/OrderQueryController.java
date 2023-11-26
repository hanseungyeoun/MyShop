package com.example.myshop.order.interfaces;

import com.example.myshop.member.interfaces.MemberPrincipal;
import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.application.OrderQueryService;
import com.example.myshop.order.dto.OrderSearchCondition;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderQueryController {

    private final OrderQueryService orderQueryService;
    private final OrderQueryDtoMapper mapper;

    @GetMapping("/{orderId}")
    public APIDataResponse<OrderResponseDto.Main> retrieveOrder(@PathVariable Long orderId , Pageable pageable) {
        OrderInfo.Main orderResult = orderQueryService.retrieveOrder(orderId);
        OrderResponseDto.Main response = mapper.of(orderResult);
        return APIDataResponse.success(response);
    }

    @GetMapping("/me")
    public APIDataResponse<Page<OrderResponseDto.Main>> retrieveOrders(
            OrderSearchCondition cond,
            @AuthenticationPrincipal MemberPrincipal authentication,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<OrderInfo.Main> orderResults = orderQueryService.retrieveOrders(cond, authentication.getId(), pageable);
        Page<OrderResponseDto.Main> response = orderResults.map(mapper::of);
        return APIDataResponse.success(response);
    }
}
