package com.example.myshop.member.application;

import com.example.myshop.member.domain.MemberLogInInfo;

import static com.example.myshop.member.application.MemberCommand.*;

public interface MemberLoginService {

    MemberLogInInfo login(MemberLoginCommand command);
}
