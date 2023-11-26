package com.example.myshop.admin.interfaces;

import com.example.myshop.admin.application.AdminLoginService;
import com.example.myshop.admin.application.AdminUserLoginInfo;
import com.example.myshop.admin.application.AdminUserService;
import com.example.myshop.response.APIDataResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminLoginService adminLoginService;
    private final AdminUserService adminUserService;
    private final AdminDtoMapper adminDtoMapper;

    @PostMapping
    public APIDataResponse<AdminDto.RegisterAdminUserResponse> register(@Valid @RequestBody AdminDto.RegisterAdminRequest request) {
        Long id = adminUserService.registerAdmin(adminDtoMapper.of(request));
        var response = adminDtoMapper.of(id);
        return APIDataResponse.success(response);
    }

    @PostMapping("/login")
    public APIDataResponse<AdminDto.AdminUserLoginResponse> login(@RequestBody AdminDto.AdminLoginRequest request) {
        AdminUserLoginInfo loinInfo = adminLoginService.login(adminDtoMapper.of(request));
        AdminDto.AdminUserLoginResponse response = adminDtoMapper.of(loinInfo);
        return APIDataResponse.success(response);
    }
}
