package com.example.myshop.member.infrastructure;

import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberJpaReader implements MemberReader {

    public final MemberJpaRepository memberJpaRepository;

    public Member getMember(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("member id not found %d", id)));
    }

    @Override
    public Optional<Member> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberJpaRepository.findById(id);
    }


    @Override
    public Page<Member> findByAll(Pageable pageable) {
        return memberJpaRepository.findAll(pageable);
    }
}
