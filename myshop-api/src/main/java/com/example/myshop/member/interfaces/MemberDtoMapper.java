package com.example.myshop.member.interfaces;

import com.example.myshop.member.application.MemberCommand;
import com.example.myshop.member.domain.MemberInfo;
import com.example.myshop.member.domain.MemberLogInInfo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import static com.example.myshop.member.interfaces.MemberDto.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberDtoMapper {
    MemberDtoMapper INSTANCE = Mappers.getMapper(MemberDtoMapper.class);

    MemberCommand.RegisterMemberCommand of(RegisterMemberRequest request);

    MemberCommand.MemberLoginCommand of(MemberLoginRequest request);

    MemberCommand.ChangeMemberPasswordCommand of(ChangeMemberPasswordRequest request);

    RegisterMemberResponse of(Long id);

    MemberLoginResponse of(MemberInfo info);

    MemberDto.MemberLoginResponse of(MemberLogInInfo info);
}
