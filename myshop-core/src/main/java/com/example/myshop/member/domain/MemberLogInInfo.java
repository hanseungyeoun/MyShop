package com.example.myshop.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLogInInfo {
    private Long id;
    private String accessToken;
}
