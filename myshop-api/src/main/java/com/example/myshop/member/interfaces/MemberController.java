package com.example.myshop.member.interfaces;

import com.example.myshop.member.domain.MemberLogInInfo;
import com.example.myshop.member.application.MemberLoginService;
import com.example.myshop.member.application.MemberService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.myshop.member.interfaces.MemberDto.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberLoginService memberLoginService;
    private final MemberService memberService;
    private final MemberDtoMapper mapper;

    @PostMapping
    public APIDataResponse<RegisterMemberResponse> register(@Valid @RequestBody RegisterMemberRequest request) {
        Long id = memberService.registerMember(mapper.of(request));
        var response = mapper.of(id);
        return APIDataResponse.success(response);
    }

    @PostMapping("/login")
    public APIDataResponse<MemberLoginResponse> login(@RequestBody MemberLoginRequest request) {
        MemberLogInInfo loinInfo = memberLoginService.login(mapper.of(request));
        MemberLoginResponse response = mapper.of(loinInfo);
        return APIDataResponse.success(response);
    }

}
