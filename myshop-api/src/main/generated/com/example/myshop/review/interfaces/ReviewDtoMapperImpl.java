package com.example.myshop.review.interfaces;

import com.example.myshop.review.application.ReviewCommand;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-04T14:54:19+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Amazon.com Inc.)"
)
@Component
public class ReviewDtoMapperImpl implements ReviewDtoMapper {

    @Override
    public ReviewCommand.RegisterReviewCommand of(ReviewDto.RegisterReviewRequest request, List<MultipartFile> reviewImages) {
        if ( request == null && reviewImages == null ) {
            return null;
        }

        ReviewCommand.RegisterReviewCommand.RegisterReviewCommandBuilder registerReviewCommand = ReviewCommand.RegisterReviewCommand.builder();

        if ( request != null ) {
            registerReviewCommand.memberId( request.getMemberId() );
            registerReviewCommand.itemId( request.getItemId() );
            registerReviewCommand.orderId( request.getOrderId() );
            registerReviewCommand.reviewText( request.getReviewText() );
            registerReviewCommand.reviewGrade( request.getReviewGrade() );
        }
        List<MultipartFile> list = reviewImages;
        if ( list != null ) {
            registerReviewCommand.reviewImages( new ArrayList<MultipartFile>( list ) );
        }

        return registerReviewCommand.build();
    }

    @Override
    public ReviewDto.RegisterReviewResponse of(Long id) {
        if ( id == null ) {
            return null;
        }

        ReviewDto.RegisterReviewResponse.RegisterReviewResponseBuilder registerReviewResponse = ReviewDto.RegisterReviewResponse.builder();

        registerReviewResponse.id( id );

        return registerReviewResponse.build();
    }

    @Override
    public ReviewCommand.UpdateReviewCommand of(ReviewDto.UpdateReviewRequest request, List<MultipartFile> reviewImages) {
        if ( request == null && reviewImages == null ) {
            return null;
        }

        ReviewCommand.UpdateReviewCommand.UpdateReviewCommandBuilder updateReviewCommand = ReviewCommand.UpdateReviewCommand.builder();

        if ( request != null ) {
            updateReviewCommand.reviewText( request.getReviewText() );
            updateReviewCommand.reviewGrade( request.getReviewGrade() );
        }
        List<MultipartFile> list = reviewImages;
        if ( list != null ) {
            updateReviewCommand.reviewImages( new ArrayList<MultipartFile>( list ) );
        }

        return updateReviewCommand.build();
    }
}
