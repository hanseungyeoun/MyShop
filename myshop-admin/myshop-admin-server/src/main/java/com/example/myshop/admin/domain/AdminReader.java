package com.example.myshop.admin.domain;

import java.util.Optional;

public interface AdminReader {
    Admin getAdminUser(Long id);

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findById(Long id);
}
