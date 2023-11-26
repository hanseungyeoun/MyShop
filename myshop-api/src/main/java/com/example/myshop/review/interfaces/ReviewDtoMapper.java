package com.example.myshop.review.interfaces;

import com.example.myshop.review.application.ReviewCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewDtoMapper {

    ReviewCommand.RegisterReviewCommand of(ReviewDto.RegisterReviewRequest request, List<MultipartFile> reviewImages);

    ReviewDto.RegisterReviewResponse of(Long id);

    ReviewCommand.UpdateReviewCommand of(ReviewDto.UpdateReviewRequest request, List<MultipartFile> reviewImages);
}
