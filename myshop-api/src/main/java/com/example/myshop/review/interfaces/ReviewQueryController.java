package com.example.myshop.review.interfaces;

import com.example.myshop.member.interfaces.MemberPrincipal;
import com.example.myshop.response.APIDataResponse;
import com.example.myshop.review.application.ReviewQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.myshop.review.interfaces.ReviewResponseDto.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewQueryController {

    private final ReviewQueryService reviewQueryService;
    private final ReviewResponseDtoMapper mapper;


    @GetMapping()
    public APIDataResponse<Page<Main>> retrieveOrders(@RequestParam("itemId") Long itemId, Pageable pageable) {
        var reviewResults = reviewQueryService.retrieveReviews(itemId, pageable);
        var response = reviewResults.map(mapper::of);
        return APIDataResponse.success(response);
    }

    @GetMapping("/{reviewId}")
    public APIDataResponse<Main> retrieveOrder(@PathVariable Long reviewId) {
        var reviewResult = reviewQueryService.retrieveReview(reviewId);
        var response = mapper.of(reviewResult);
        return APIDataResponse.success(response);
    }

    @GetMapping("me/writeAble")
    public APIDataResponse<List<WriteAbleReviewResponse>> retrieveWriteAble (
            @AuthenticationPrincipal MemberPrincipal authentication
    ) {
        var reviewResult = reviewQueryService.retrieveWriteAbleReviews(authentication.getId());
        var response = reviewResult.stream().map(mapper::of).toList();
        return APIDataResponse.success(response);
    }
}
