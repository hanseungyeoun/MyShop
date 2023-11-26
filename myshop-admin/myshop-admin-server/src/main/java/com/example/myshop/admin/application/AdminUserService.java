package com.example.myshop.admin.application;

public interface AdminUserService {

    Long registerAdmin(AdminUserCommand.RegisterAdminCommand command);
    AdminInfo changePassword(AdminUserCommand.ChangeAdminPasswordCommand command);
}
