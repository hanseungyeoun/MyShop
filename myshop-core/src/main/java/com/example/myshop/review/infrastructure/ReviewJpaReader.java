package com.example.myshop.review.infrastructure;


import com.example.myshop.review.domain.ReviewInfo;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReviewJpaReader implements ReviewReader {

    private final ReviewJpaRepository repository;

    @Override
    public Review getReview(Long reviewId) {
        return repository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Review id not found %d", reviewId)));
    }

    @Override
    public ReviewInfo.Main findReviewById(Long reviewId) {
        return repository.findReviewById(reviewId);
    }

    @Override
    public Page<ReviewInfo.Main> findAllByItemId(Long itemId, Pageable pageable) {
        return repository.findAllByItemId(itemId, pageable);
    }

    @Override
    public List<ReviewInfo.WriteAbleReviewInfo> getWriteAbleReviewList(Long memberId) {
        return repository.findWriteAbleReviewList(memberId);
    }
}
