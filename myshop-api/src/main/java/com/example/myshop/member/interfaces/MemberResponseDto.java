package com.example.myshop.member.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDto {

    private final Long id;
    private final String username;
    private final String memberGrade;
    private final String memberGradeDescription;
}
