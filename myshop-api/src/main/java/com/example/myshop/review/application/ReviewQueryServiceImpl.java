package com.example.myshop.review.application;

import com.example.myshop.review.domain.ReviewInfo;
import com.example.myshop.review.domain.ReviewReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewReader reviewReader;
    private final ReviewInfoMapper reviewInfoMapper;

    public Page<ReviewInfo.Main> retrieveReviews(Long itemId, Pageable pageable) {
        return reviewReader.findAllByItemId(itemId, pageable);
    }

    public ReviewInfo.Main retrieveReview(Long reviewId) {
        return reviewReader.findReviewById(reviewId);
    }

    public List<ReviewInfo.WriteAbleReviewInfo> retrieveWriteAbleReviews(Long memberId) {
        return reviewReader.getWriteAbleReviewList(memberId);
    }
}
