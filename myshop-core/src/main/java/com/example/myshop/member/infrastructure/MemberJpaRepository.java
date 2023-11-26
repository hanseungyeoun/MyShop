package com.example.myshop.member.infrastructure;

import com.example.myshop.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUsername(String userNam);
}