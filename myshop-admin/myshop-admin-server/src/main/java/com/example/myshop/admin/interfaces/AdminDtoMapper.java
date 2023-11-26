package com.example.myshop.admin.interfaces;

import com.example.myshop.admin.application.AdminUserLoginInfo;
import com.example.myshop.admin.application.AdminUserCommand;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminDtoMapper {
    AdminDtoMapper INSTANCE = Mappers.getMapper(AdminDtoMapper.class);

    AdminUserCommand.RegisterAdminCommand of(AdminDto.RegisterAdminRequest request);

    AdminUserCommand.AdminLoginCommand of(AdminDto.AdminLoginRequest request);

    AdminUserCommand.ChangeAdminPasswordCommand of(AdminDto.ChangeAdminPasswordRequest request);

    AdminDto.RegisterAdminUserResponse of(Long id);

    AdminDto.AdminUserLoginResponse of(AdminUserLoginInfo info);
}
