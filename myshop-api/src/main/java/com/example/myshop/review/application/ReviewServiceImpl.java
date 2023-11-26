package com.example.myshop.review.application;

import com.example.myshop.member.domain.Member;
import com.example.myshop.member.domain.MemberReader;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemReader;
import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.item.infrastructure.item.FileUploaderAdapter;
import com.example.myshop.review.domain.Review;
import com.example.myshop.review.domain.ReviewImage;
import com.example.myshop.review.domain.ReviewReader;
import com.example.myshop.review.domain.ReviewStore;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static com.example.myshop.review.application.ReviewCommand.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewServiceImpl implements ReviewService {

    private final MemberReader memberReader;
    private final OrderReader orderReader;
    private final ItemReader itemReader;
    private final ReviewReader reviewReader;
    private final ReviewStore reviewStore;
    private final FileUploaderAdapter fileUploder;

    @Override
    public Long registerReview(RegisterReviewCommand command) {
        validateReviewRequest(command.getMemberId(), command.getItemId(), command.getOrderId());
        List<ReviewImage> itemImages = getItemImages(command.getReviewImages());

        Review initReview = command.toEntity(itemImages);
        Review storedReview = reviewStore.store(initReview);
        return storedReview.getId();
    }

    @Override
    public void updateReview(Long reviewId, UpdateReviewCommand command) {
        Review review = reviewReader.getReview(reviewId);
        review.updateInfo(command.getReviewText(), command.getReviewGrade());
        review.clearImages();
        review.addImages(getItemImages(command.getReviewImages()));
    }

    @Override
    public void deleteReview(Long reviewId) {
        reviewStore.delete(reviewId);
    }

    private void validateReviewRequest(Long memberId, Long itemId, Long orderId) {
        Optional<Member> optionalMember = memberReader.findById(memberId);
        if (optionalMember.isEmpty()) {
            throw new EntityNotFoundException(String.format("member id not found %d", memberId));
        }

        Optional<Item> optionalItem = itemReader.findById(itemId);
        if (optionalItem.isEmpty()) {
            throw new EntityNotFoundException(String.format("item id not found %d", itemId));
        }

        Order order = orderReader.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("order id not found %d", orderId)));

        order.verifyCanWriteReview();
    }

    private List<ReviewImage> getItemImages(List<MultipartFile> reviewImages) {
        List<ReviewImage> itemImages;
        try {
            itemImages = fileUploder.storeFiles(reviewImages).stream().map(ReviewImage::new).toList();
        } catch (IOException e) {
            throw new IllegalStatusException();
        }

        return itemImages;
    }

}
