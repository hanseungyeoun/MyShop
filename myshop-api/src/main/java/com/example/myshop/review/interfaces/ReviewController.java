package com.example.myshop.review.interfaces;

import com.example.myshop.response.APIDataResponse;
import com.example.myshop.review.application.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.myshop.review.application.ReviewCommand.*;
import static com.example.myshop.review.interfaces.ReviewDto.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewDtoMapper reviewDtoMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public APIDataResponse<RegisterReviewResponse> registerReview(
            @RequestPart @Valid RegisterReviewRequest reviewInfo,
            @RequestPart(value = "itemImages") List<MultipartFile> reviewImages
    ) {
        var reviewCommand = reviewDtoMapper.of(reviewInfo, reviewImages);
        var reviewId = reviewService.registerReview(reviewCommand);
        var response = reviewDtoMapper.of(reviewId);
        return APIDataResponse.success(response);
    }

    @PostMapping("/{reviewId}/update")
    public APIDataResponse<String> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestPart UpdateReviewRequest updateReviewInfo,
            List<MultipartFile> reviewImages
    ) {
        UpdateReviewCommand reviewCommand = reviewDtoMapper.of(updateReviewInfo, reviewImages);
        reviewService.updateReview(reviewId, reviewCommand);
        return APIDataResponse.success("OK");
    }

    @DeleteMapping("/{reviewId}")
    public APIDataResponse<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return APIDataResponse.success("OK");
    }
}
