package com.example.myshop.review.application;

import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewInfo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ReviewInfoMapperImpl implements ReviewInfoMapper {

    @Override
    public ReviewInfo.Main of(Review review) {
        if ( review == null ) {
            return null;
        }

        ReviewInfo.Main main = new ReviewInfo.Main();

        main.setReviewImages( ReviewInfoMapper.itemImageToString( review.getReviewImages() ) );
        main.setId( review.getId() );
        main.setMemberId( review.getMemberId() );
        main.setItemId( review.getItemId() );
        main.setOrderId( review.getOrderId() );
        main.setReviewText( review.getReviewText() );
        if ( review.getReviewGrade() != null ) {
            main.setReviewGrade( review.getReviewGrade().name() );
        }

        return main;
    }
}
