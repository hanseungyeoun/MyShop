package com.example.myshop.review.domain;

import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.common.jpa.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review_images")
@ToString(callSuper = true)
@Entity
public class ReviewImage extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_image_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private String imagePath;

    //==생성 메서드==//
    @Builder
    public ReviewImage(String imagePath) {
        if (!StringUtils.hasText(imagePath)) throw new InvalidParamException("ReviewImage.ReviewImage");

        this.imagePath = imagePath;
    }
}