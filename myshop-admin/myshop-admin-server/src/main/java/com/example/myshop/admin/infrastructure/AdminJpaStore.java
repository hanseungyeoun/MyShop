package com.example.myshop.admin.infrastructure;

import com.example.myshop.admin.domain.Admin;
import com.example.myshop.admin.domain.AdminStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminJpaStore implements AdminStore {

    public final AdminJpaRepository adminJpaRepository;

    @Override
    public Admin store(Admin member) {
        return adminJpaRepository.save(member);
    }
}
