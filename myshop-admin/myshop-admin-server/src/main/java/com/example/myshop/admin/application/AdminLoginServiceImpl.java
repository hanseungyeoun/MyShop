package com.example.myshop.admin.application;

import com.example.myshop.admin.domain.Admin;
import com.example.myshop.admin.domain.AdminReader;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.utils.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AdminLoginServiceImpl implements AdminLoginService {

    private final AdminReader memberReader;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder;

    @Override
    public AdminUserLoginInfo login(AdminUserCommand.AdminLoginCommand command) {
        Admin member = memberReader.findByUsername(command.getUsername())
                .orElseThrow(() -> new EntityNotFoundException(String.format("member name not found %s", command.getUsername())));

        member.checkPasswordMatch(encoder, command.getPassword());
        return new AdminUserLoginInfo(member.getId(), tokenProvider.createToken(member.getUsername()));
    }
}
