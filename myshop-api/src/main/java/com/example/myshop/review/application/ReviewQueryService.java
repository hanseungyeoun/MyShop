package com.example.myshop.review.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.myshop.review.domain.ReviewInfo.*;

public interface ReviewQueryService {

    Page<Main> retrieveReviews(Long itemId, Pageable pageable) ;
    Main retrieveReview(Long reviewId);
    List<WriteAbleReviewInfo> retrieveWriteAbleReviews(Long memberId);
}
