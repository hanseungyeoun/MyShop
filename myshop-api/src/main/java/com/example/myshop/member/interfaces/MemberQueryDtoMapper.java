package com.example.myshop.member.interfaces;

import com.example.myshop.member.domain.MemberInfo;
import com.example.myshop.order.interfaces.OrderResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberQueryDtoMapper {

    @Mapping(expression = "java(memberResult.getMemberGrade().name())", target = "memberGrade")
    @Mapping(expression = "java(memberResult.getMemberGrade().getDescription())", target = "memberGradeDescription")
    MemberResponseDto of(MemberInfo memberResult);
}
