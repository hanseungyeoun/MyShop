package com.example.myshop.admin.infrastructure;

import com.example.myshop.admin.domain.Admin;
import com.example.myshop.admin.domain.AdminReader;
import com.example.myshop.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminJpaReader implements AdminReader {

    public final AdminJpaRepository adminJpaRepository;

    public Admin getAdminUser(Long id) {
        return adminJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("member id not found %d", id)));
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        return adminJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Admin> findById(Long id) {
        return adminJpaRepository.findById(id);
    }
}
