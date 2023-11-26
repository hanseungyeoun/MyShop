package com.example.myshop.member.interfaces;

import com.example.myshop.member.application.MemberCommand;
import com.example.myshop.member.domain.MemberInfo;
import com.example.myshop.member.domain.MemberLogInInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:20+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {

    @Override
    public MemberCommand.RegisterMemberCommand of(MemberDto.RegisterMemberRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.RegisterMemberCommand.RegisterMemberCommandBuilder registerMemberCommand = MemberCommand.RegisterMemberCommand.builder();

        registerMemberCommand.username( request.getUsername() );
        registerMemberCommand.password( request.getPassword() );

        return registerMemberCommand.build();
    }

    @Override
    public MemberCommand.MemberLoginCommand of(MemberDto.MemberLoginRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.MemberLoginCommand.MemberLoginCommandBuilder memberLoginCommand = MemberCommand.MemberLoginCommand.builder();

        memberLoginCommand.username( request.getUsername() );
        memberLoginCommand.password( request.getPassword() );

        return memberLoginCommand.build();
    }

    @Override
    public MemberCommand.ChangeMemberPasswordCommand of(MemberDto.ChangeMemberPasswordRequest request) {
        if ( request == null ) {
            return null;
        }

        MemberCommand.ChangeMemberPasswordCommand.ChangeMemberPasswordCommandBuilder changeMemberPasswordCommand = MemberCommand.ChangeMemberPasswordCommand.builder();

        changeMemberPasswordCommand.id( request.getId() );
        changeMemberPasswordCommand.oldPassword( request.getOldPassword() );
        changeMemberPasswordCommand.newPassword( request.getNewPassword() );

        return changeMemberPasswordCommand.build();
    }

    @Override
    public MemberDto.RegisterMemberResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        MemberDto.RegisterMemberResponse.RegisterMemberResponseBuilder registerMemberResponse = MemberDto.RegisterMemberResponse.builder();

        registerMemberResponse.id( id );

        return registerMemberResponse.build();
    }

    @Override
    public MemberDto.MemberLoginResponse of(MemberInfo info) {
        if ( info == null ) {
            return null;
        }

        MemberDto.MemberLoginResponse.MemberLoginResponseBuilder memberLoginResponse = MemberDto.MemberLoginResponse.builder();

        memberLoginResponse.id( info.getId() );

        return memberLoginResponse.build();
    }

    @Override
    public MemberDto.MemberLoginResponse of(MemberLogInInfo info) {
        if ( info == null ) {
            return null;
        }

        MemberDto.MemberLoginResponse.MemberLoginResponseBuilder memberLoginResponse = MemberDto.MemberLoginResponse.builder();

        memberLoginResponse.id( info.getId() );
        memberLoginResponse.accessToken( info.getAccessToken() );

        return memberLoginResponse.build();
    }
}
