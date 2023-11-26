package com.example.myshop.member.application;

import com.example.myshop.member.domain.MemberInfo;
import com.example.myshop.member.domain.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberQueryService {

    private final MemberReader memberReader;

    public MemberInfo retrieveMember(Long memberId) {
        return MemberInfo.formEntity(memberReader.getMember(memberId));
    }

    public Page<MemberInfo> retrieveMembers(Pageable pageable) {
        return memberReader.findByAll(pageable).map(MemberInfo::formEntity);
    }
}
