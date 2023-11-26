package com.example.myshop.review.domain;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.common.jpa.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
@Entity
@ToString
public class Review extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Version
    private Integer version;

    @Column(nullable = false)
    private Long memberId;
    @Column(nullable = false)
    private Long itemId;
    @Column(nullable = false)
    private Long orderId;

    @Lob
    @Column(nullable = false)
    private String reviewText;
    @Column(nullable = false)
    private ReviewGrade reviewGrade;

    @ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review", fetch = FetchType.LAZY, orphanRemoval = true)
    private final List<ReviewImage> reviewImages = new ArrayList<>();

    //==생성 메서드==//
    @Builder
    public Review(Long memberId, Long itemId, Long orderId, String reviewText, ReviewGrade reviewGrade, List<ReviewImage> reviewImages) {
        if (memberId == null) throw new InvalidParamException("review.memberId");
        if (itemId == null) throw new InvalidParamException("review.itemId");
        if (orderId == null) throw new InvalidParamException("review.orderId");
        if (!StringUtils.hasText(reviewText)) throw new InvalidParamException("review.reviewText");
        if (reviewImages == null) throw new InvalidParamException("review.orderId");

        this.memberId = memberId;
        this.itemId = itemId;
        this.orderId = orderId;
        this.reviewText = reviewText;
        this.reviewGrade = reviewGrade;
        addImages(reviewImages);
    }
    public void clearImages() {
        reviewImages.clear();
    }

    public void addImages(List<ReviewImage> itemImages) {
        for (ReviewImage itemImage : itemImages) {
            addImages(itemImage);
        }
    }

    //==비즈니스 로직==//
    public void updateInfo(String reviewText, ReviewGrade reviewGrade) {
        this.reviewText = reviewText;
        this.reviewGrade = reviewGrade;
    }

    private void addImages(ReviewImage reviewImage) {
        this.reviewImages.add(reviewImage);
        reviewImage.setReview(this);
    }
}
