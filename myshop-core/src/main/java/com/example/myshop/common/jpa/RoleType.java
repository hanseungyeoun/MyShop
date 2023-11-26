package com.example.myshop.common.jpa;

import lombok.Getter;

@Getter
public enum RoleType {

    ROLE_PARTNER("Partner"),
    ROLE_ADMIN("Admin"),
    ROLE_USER("User");

    private final String name;

    RoleType(String name) {
        this.name = name;
    }
}