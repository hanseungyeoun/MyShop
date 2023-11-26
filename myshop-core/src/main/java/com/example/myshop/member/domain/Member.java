package com.example.myshop.member.domain;

import com.example.myshop.exception.BaseException;
import com.example.myshop.exception.ErrorCode;
import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.common.jpa.Password;
import com.example.myshop.common.jpa.RoleType;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "members")
@Entity
public class Member extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(unique=true)
    private String username;

    @Embedded
    private Password password;

    @Enumerated(EnumType.STRING)
    private MemberGrade memberGrade = MemberGrade.BRONZE;

    @Enumerated(EnumType.STRING)
    private RoleType role = RoleType.ROLE_USER;

    @Builder
    private Member(
            String username,
            Password password,
            RoleType role
    ) {
        if (!StringUtils.hasText(username)) throw new InvalidParamException("Member.username");
        if (password == null) throw new InvalidParamException("MemberAccount.password");
        this.username = username;
        this.password = password;
        this.role = role;
        this.memberGrade = MemberGrade.BRONZE;
    }

    public boolean checkPasswordMatch(PasswordEncoder encoder, String pw) {
        if (!password.match(encoder, pw)) {
            throw new BaseException(ErrorCode.INVALID_PASSWORD);
        }
        return true;
    }

    public void changePassword(PasswordEncoder encoder, String oldPw, String newPw) {
        checkPasswordMatch(encoder, oldPw);
        this.password = new Password(encoder.encode(newPw));
    }

    public void changeMemberGrade(MemberGrade newMemberGrade) {
        this.memberGrade = newMemberGrade;
    }
}
