package com.example.myshop.member.interfaces;

import com.example.myshop.member.application.MemberQueryService;
import com.example.myshop.member.domain.MemberInfo;
import com.example.myshop.order.application.OrderQueryService;
import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.dto.OrderSearchCondition;
import com.example.myshop.order.interfaces.OrderQueryDtoMapper;
import com.example.myshop.order.interfaces.OrderResponseDto;
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
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberQueryController {

    private final MemberQueryService memberQueryService;
    private final MemberQueryDtoMapper mapper;

    @GetMapping("/{memberId}")
    public APIDataResponse<MemberResponseDto> retrieveMembers(@PathVariable Long memberId , Pageable pageable) {
        MemberInfo memberResult = memberQueryService.retrieveMember(memberId);
        MemberResponseDto response = mapper.of(memberResult);
        return APIDataResponse.success(response);
    }

    @GetMapping("")
    public APIDataResponse<Page<MemberResponseDto>> retrieveMembers(
            @AuthenticationPrincipal MemberPrincipal authentication,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MemberInfo> membersResults = memberQueryService.retrieveMembers(pageable);
        Page<MemberResponseDto> response = membersResults.map(mapper::of);
        return APIDataResponse.success(response);
    }
}
