package com.example.myshop.review.application;

import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewImage;
import com.example.myshop.review.domain.ReviewInfo;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ReviewInfoMapper {

    @Mapping(source = "review.reviewImages", target = "reviewImages", qualifiedByName = "reviewImageToStringList")
    ReviewInfo.Main of(Review review);

    @Named("reviewImageToStringList")
    static List<String> itemImageToString(List<ReviewImage> images) {
        return images == null ? new ArrayList<>() : images.stream().map(ReviewImage::getImagePath).toList();
    }
}
