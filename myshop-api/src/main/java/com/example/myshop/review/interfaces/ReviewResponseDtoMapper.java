package com.example.myshop.review.interfaces;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static com.example.myshop.review.domain.ReviewInfo.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewResponseDtoMapper {
    ReviewResponseDto.Main of(Main mainResult);

    ReviewResponseDto.WriteAbleReviewResponse of (WriteAbleReviewInfo mainResult);
}
