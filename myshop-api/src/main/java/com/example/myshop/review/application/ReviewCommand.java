package com.example.myshop.review.application;


import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewGrade;
import com.example.myshop.review.domain.ReviewImage;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewCommand {

    @Builder
    @Getter
    @ToString
    public static class RegisterReviewCommand {
        private final Long memberId;
        private final Long itemId;
        private final Long orderId;
        private final String reviewText;
        private final ReviewGrade reviewGrade;
        private final List<MultipartFile> reviewImages;

        Review toEntity(List<ReviewImage> reviewImages) {
            return Review.builder()
                    .memberId(memberId)
                    .itemId(itemId)
                    .orderId(orderId)
                    .reviewText(reviewText)
                    .reviewGrade(reviewGrade)
                    .reviewImages(reviewImages)
                    .build();
        }
    }

    @Builder
    @Getter
    @ToString
    public static class UpdateReviewCommand {
        private final String reviewText;
        private final ReviewGrade reviewGrade;
        private final List<MultipartFile> reviewImages;
    }
}
