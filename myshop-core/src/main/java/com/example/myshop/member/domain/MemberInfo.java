package com.example.myshop.member.domain;

import lombok.Getter;

@Getter
public class MemberInfo {
    private Long id;
    private String username;
    private MemberGrade memberGrade;

    public static MemberInfo formEntity(Member member) {
        MemberInfo memberInfo = new MemberInfo();
        memberInfo.id = member.getId();
        memberInfo.username = member.getUsername();
        memberInfo.memberGrade = member.getMemberGrade();
        return memberInfo;
    }
}
