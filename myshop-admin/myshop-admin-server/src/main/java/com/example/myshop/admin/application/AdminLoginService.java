package com.example.myshop.admin.application;

public interface AdminLoginService {

    AdminUserLoginInfo login(AdminUserCommand.AdminLoginCommand command);
}
