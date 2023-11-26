package com.example.myshop.review.interfaces;

import com.example.myshop.review.domain.ReviewGrade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReviewDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class RegisterReviewRequest {
        @NotNull
        private Long memberId;

        @NotNull
        private Long itemId;

        @NotNull
        private Long orderId;

        @NotBlank
        private String reviewText;

        @NotNull
        private ReviewGrade reviewGrade;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterReviewResponse {
        private final Long id;
    }

    @Getter
    @ToString
    @NoArgsConstructor
    public static class UpdateReviewRequest {
        @NotNull
        private String reviewText;
        @NotNull
        private ReviewGrade reviewGrade;
    }
}
