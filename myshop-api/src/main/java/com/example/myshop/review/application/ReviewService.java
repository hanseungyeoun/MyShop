package com.example.myshop.review.application;

import static com.example.myshop.review.application.ReviewCommand.*;

public interface ReviewService {

    Long registerReview(RegisterReviewCommand command);

    void updateReview(Long reviewId, ReviewCommand.UpdateReviewCommand reviewCommand);

    void deleteReview(Long reviewId);
}
