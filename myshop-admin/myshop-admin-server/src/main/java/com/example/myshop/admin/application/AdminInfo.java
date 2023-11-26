package com.example.myshop.admin.application;

import com.example.myshop.admin.domain.Admin;
import lombok.Getter;

@Getter
public class AdminInfo {
    private Long id;
    private String username;

    public static AdminInfo formEntity(Admin member) {
        AdminInfo adminInfo = new AdminInfo();
        adminInfo.id = member.getId();
        adminInfo.username = member.getUsername();
        return adminInfo;
    }
}
