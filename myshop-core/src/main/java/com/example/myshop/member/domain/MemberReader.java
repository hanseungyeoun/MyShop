package com.example.myshop.member.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface MemberReader {
    Member getMember(Long id);

    Optional<Member> findByUsername(String username);

    Optional<Member> findById(Long id);

    Page<Member> findByAll(Pageable pageable);
}
