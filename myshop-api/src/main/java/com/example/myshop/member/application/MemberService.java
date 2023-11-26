package com.example.myshop.member.application;

import com.example.myshop.member.application.MemberCommand.RegisterMemberCommand;
import com.example.myshop.member.domain.MemberGrade;
import com.example.myshop.member.domain.MemberInfo;

public interface MemberService {

    Long registerMember(RegisterMemberCommand command);
    MemberInfo changePassword(MemberCommand.ChangeMemberPasswordCommand command);
    MemberInfo changeMemberGrade(Long memberId, MemberGrade grade);
}
