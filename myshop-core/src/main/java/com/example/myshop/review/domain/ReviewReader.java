package com.example.myshop.review.domain;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.myshop.review.domain.ReviewInfo.*;

public interface ReviewReader {
    Review getReview(Long reviewId);

    Main findReviewById(Long reviewId);

    Page<Main> findAllByItemId(Long itemId, Pageable pageable);

    List<WriteAbleReviewInfo> getWriteAbleReviewList(Long memberId);
}
