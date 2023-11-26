package com.example.myshop.member.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class MemberDto {

    private MemberDto() {
        throw new IllegalStateException("Utility class");
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterMemberRequest {
        private @Size(min = 3, max = 10) @NotBlank
        String username;

        private @Size(min = 3, max = 10) @NotBlank
        String password;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterMemberResponse {
        private final Long id;
    }

    @Getter
    @Setter
    @ToString
    public static class MemberLoginRequest {
        @Size(min = 3, max = 10) @NotBlank String username;
        @Size(min = 3, max = 10) @NotBlank String password;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeMemberPasswordRequest {
        @NotNull private
        Long id;

        @Size(min = 3, max = 10)
        @NotBlank
        private String oldPassword;

        @Size(min = 3, max = 10)
        @NotBlank
        private String newPassword;
    }


    @Getter
    @Builder
    @ToString
    public static class MemberLoginResponse {
        private Long id;
        private String accessToken;
    }

}
