package com.example.myshop.member.infrastructure;

import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberJpaStore implements MemberStore {

    public final MemberJpaRepository memberJpaRepository;

    @Override
    public Member store(Member member) {
        return memberJpaRepository.save(member);
    }
}
