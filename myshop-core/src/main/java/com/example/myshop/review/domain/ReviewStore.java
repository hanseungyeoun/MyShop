package com.example.myshop.review.domain;

public interface ReviewStore {

    Review store(Review review);
    void delete(Long reviewId);
    void flush();
}
