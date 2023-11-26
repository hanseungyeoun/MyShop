package com.example.myshop.member.application;

import com.example.myshop.common.jpa.Password;
import com.example.myshop.common.jpa.RoleType;
import com.example.myshop.member.application.MemberCommand.RegisterMemberCommand;
import com.example.myshop.exception.BaseException;
import com.example.myshop.exception.ErrorCode;
import com.example.myshop.member.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final PasswordEncoder encoder;

    @Override
    public Long registerMember(RegisterMemberCommand command) {
        checkExistUseName(command.getUsername());

        Password password = new Password(encoder.encode(command.getPassword()));
        Member member = Member.builder()
                .username(command.getUsername())
                .password(password)
                .role(RoleType.ROLE_USER)
                .build();

        Member storedMember = memberStore.store(member);
        return storedMember.getId();
    }

    private void checkExistUseName(String username) {
        memberReader.findByUsername(username).ifPresent(userAccount -> {
            throw new BaseException(String.format("%s (%s)", ErrorCode.DUPLICATED_USER_NAME, username), ErrorCode.DUPLICATED_USER_NAME);
        });
    }

    @Override
    public MemberInfo changePassword(MemberCommand.ChangeMemberPasswordCommand command) {
        Member member = memberReader.getMember(command.getId());
        member.changePassword(encoder, command.getOldPassword(), command.getNewPassword());
        return MemberInfo.formEntity(member);
    }

    @Override
    public MemberInfo changeMemberGrade(Long memberId, MemberGrade grade) {
        Member member = memberReader.getMember(memberId);
        member.changeMemberGrade(grade);
        return MemberInfo.formEntity(member);
    }

}
