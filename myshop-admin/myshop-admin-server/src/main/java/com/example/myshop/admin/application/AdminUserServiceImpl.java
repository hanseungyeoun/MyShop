package com.example.myshop.admin.application;

import com.example.myshop.admin.domain.Admin;
import com.example.myshop.admin.domain.AdminReader;
import com.example.myshop.admin.domain.AdminStore;
import com.example.myshop.common.jpa.Password;
import com.example.myshop.common.jpa.RoleType;
import com.example.myshop.exception.BaseException;
import com.example.myshop.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.myshop.admin.application.AdminUserCommand.*;


@RequiredArgsConstructor
@Transactional
@Service
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminReader adminReader;
    private final AdminStore adminStore;
    private final PasswordEncoder encoder;

    @Override
    public Long registerAdmin(RegisterAdminCommand command) {
        adminReader.findByUsername(command.getUsername()).ifPresent(userAccount -> {
            throw new BaseException(String.format("%s (%s)", ErrorCode.DUPLICATED_USER_NAME, command.getUsername()), ErrorCode.DUPLICATED_USER_NAME);
        });

        Password password = new Password(encoder.encode(command.getPassword()));

        Admin member = Admin.builder()
                .username(command.getUsername())
                .password(password)
                .role(RoleType.ROLE_ADMIN)
                .build();

        Admin storedMember = adminStore.store(member);
        return storedMember.getId();
    }

    @Override
    public AdminInfo changePassword(ChangeAdminPasswordCommand command) {
        Admin admin = adminReader.getAdminUser(command.getId());
        admin.changePassword(encoder, command.getOldPassword(), command.getNewPassword());
        return AdminInfo.formEntity(admin);
    }
}
