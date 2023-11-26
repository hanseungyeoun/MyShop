package com.example.myshop.item.domain.item;

import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_images")
@ToString(callSuper = true)
@Entity
public class ItemImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String imagePath;

    @Builder
    public ItemImage(String imagePath) {
        if (imagePath == null) throw new InvalidParamException("ItemImage.imagePath");

        this.imagePath = imagePath;
    }
}
