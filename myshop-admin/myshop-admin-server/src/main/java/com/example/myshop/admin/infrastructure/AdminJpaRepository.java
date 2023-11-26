package com.example.myshop.admin.infrastructure;

import com.example.myshop.admin.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String userNam);
}