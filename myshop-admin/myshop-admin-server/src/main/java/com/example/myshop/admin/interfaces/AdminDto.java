package com.example.myshop.admin.interfaces;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

class AdminDto {

    private AdminDto() {
        throw new IllegalStateException("Utility class");
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterAdminRequest {
        private @Size(min = 3, max = 10) @NotBlank
        String username;

        private @Size(min = 3, max = 10) @NotBlank
        String password;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterAdminUserResponse {
        private final Long id;
    }

    @Getter
    @Setter
    @ToString
    public static class AdminLoginRequest {
        @Size(min = 3, max = 10) @NotBlank String username;
        @Size(min = 3, max = 10) @NotBlank String password;
    }

    @Getter
    @Builder
    @ToString
    public static class ChangeAdminPasswordRequest {
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
    public static class AdminUserLoginResponse {
        private Long id;
        private String accessToken;
    }

}
