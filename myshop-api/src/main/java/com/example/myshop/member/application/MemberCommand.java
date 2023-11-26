package com.example.myshop.member.application;

import com.example.myshop.common.jpa.RoleType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class MemberCommand {

    private MemberCommand() {
        throw new IllegalStateException("Utility class");
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterMemberCommand {
        private final String username;
        private final String password;
        private final RoleType roleType = RoleType.ROLE_USER;
    }

    @Getter
    @Builder
    @ToString
    public static class MemberLoginCommand {
        private final String username;
        private final String password;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeMemberPasswordCommand {
        private final Long id;
        private final String oldPassword;
        private final String newPassword;
    }
}
