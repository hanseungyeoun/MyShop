package com.example.myshop.review.infrastructure.querydsl;


import com.example.myshop.review.domain.ReviewInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {
    ReviewInfo.Main findReviewById(Long reviewId);

    Page<ReviewInfo.Main> findAllByItemId(Long itemId, Pageable pageable);

    List<ReviewInfo.WriteAbleReviewInfo> findWriteAbleReviewList(Long memberId);
}
