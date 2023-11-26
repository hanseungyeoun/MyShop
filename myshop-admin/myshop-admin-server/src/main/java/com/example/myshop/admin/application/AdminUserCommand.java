package com.example.myshop.admin.application;

import com.example.myshop.common.jpa.RoleType;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminUserCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterAdminCommand {
        private final String username;
        private final String password;
        private final RoleType roleType = RoleType.ROLE_ADMIN;
    }

    @Getter
    @Builder
    @ToString
    public static class AdminLoginCommand {
        private final String username;
        private final String password;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeAdminPasswordCommand {
        private final Long id;
        private final String oldPassword;
        private final String newPassword;
    }
}
