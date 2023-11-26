package com.example.myshop.review.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static com.example.myshop.CoreFixtures.*;

class ReviewTest {

    @Test
    void 리뷰_변경(){
        //given
        Review review = aReview().build();

        //when
        review.updateInfo("좋아요", ReviewGrade.ONE);

        //then
        Assertions.assertThat(review.getReviewText()).isEqualTo("좋아요");
        Assertions.assertThat(review.getReviewGrade()).isEqualTo(ReviewGrade.ONE);
    }

}