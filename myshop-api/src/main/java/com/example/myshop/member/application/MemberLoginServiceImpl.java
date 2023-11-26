package com.example.myshop.member.application;

import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberLogInInfo;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberLoginServiceImpl implements MemberLoginService {

    private final MemberReader memberReader;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    @Override
    public MemberLogInInfo login(MemberCommand.MemberLoginCommand command) {
        Member member = memberReader.findByUsername(command.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("member name not found %s", command.getUsername())));

        member.checkPasswordMatch(encoder, command.getPassword());
        return new MemberLogInInfo(member.getId(), tokenProvider.createToken(member.getUsername()));
    }
}
