package com.example.myshop.review.infrastructure;


import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewJpaStore implements ReviewStore {

    private final ReviewJpaRepository repository;

    private final ReviewImageRepository reviewImageRepository;

    @Override
    public Review store(Review review) {
        return repository.save(review);
    }


    @Override
    public void delete(Long reviewId) {
        repository.deleteById(reviewId);
    }

    @Override
    public void flush() {
        repository.flush();
    }
}
