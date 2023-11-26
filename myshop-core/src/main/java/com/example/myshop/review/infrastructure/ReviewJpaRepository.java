package com.example.myshop.review.infrastructure;

import com.example.myshop.review.domain.Review;
import com.example.myshop.review.infrastructure.querydsl.ReviewRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJpaRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
}