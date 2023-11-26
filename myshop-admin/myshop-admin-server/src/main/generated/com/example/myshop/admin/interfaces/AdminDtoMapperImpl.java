package com.example.myshop.admin.interfaces;

import com.example.myshop.admin.application.AdminUserCommand;
import com.example.myshop.admin.application.AdminUserLoginInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T15:30:31+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class AdminDtoMapperImpl implements AdminDtoMapper {

    @Override
    public AdminUserCommand.RegisterAdminCommand of(AdminDto.RegisterAdminRequest request) {
        if ( request == null ) {
            return null;
        }

        AdminUserCommand.RegisterAdminCommand.RegisterAdminCommandBuilder registerAdminCommand = AdminUserCommand.RegisterAdminCommand.builder();

        registerAdminCommand.username( request.getUsername() );
        registerAdminCommand.password( request.getPassword() );

        return registerAdminCommand.build();
    }

    @Override
    public AdminUserCommand.AdminLoginCommand of(AdminDto.AdminLoginRequest request) {
        if ( request == null ) {
            return null;
        }

        AdminUserCommand.AdminLoginCommand.AdminLoginCommandBuilder adminLoginCommand = AdminUserCommand.AdminLoginCommand.builder();

        adminLoginCommand.username( request.getUsername() );
        adminLoginCommand.password( request.getPassword() );

        return adminLoginCommand.build();
    }

    @Override
    public AdminUserCommand.ChangeAdminPasswordCommand of(AdminDto.ChangeAdminPasswordRequest request) {
        if ( request == null ) {
            return null;
        }

        AdminUserCommand.ChangeAdminPasswordCommand.ChangeAdminPasswordCommandBuilder changeAdminPasswordCommand = AdminUserCommand.ChangeAdminPasswordCommand.builder();

        changeAdminPasswordCommand.id( request.getId() );
        changeAdminPasswordCommand.oldPassword( request.getOldPassword() );
        changeAdminPasswordCommand.newPassword( request.getNewPassword() );

        return changeAdminPasswordCommand.build();
    }

    @Override
    public AdminDto.RegisterAdminUserResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        AdminDto.RegisterAdminUserResponse.RegisterAdminUserResponseBuilder registerAdminUserResponse = AdminDto.RegisterAdminUserResponse.builder();

        registerAdminUserResponse.id( id );

        return registerAdminUserResponse.build();
    }

    @Override
    public AdminDto.AdminUserLoginResponse of(AdminUserLoginInfo info) {
        if ( info == null ) {
            return null;
        }

        AdminDto.AdminUserLoginResponse.AdminUserLoginResponseBuilder adminUserLoginResponse = AdminDto.AdminUserLoginResponse.builder();

        adminUserLoginResponse.id( info.getId() );
        adminUserLoginResponse.accessToken( info.getAccessToken() );

        return adminUserLoginResponse.build();
    }
}
