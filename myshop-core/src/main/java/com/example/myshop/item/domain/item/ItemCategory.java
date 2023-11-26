package com.example.myshop.item.domain.item;

import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "item_category")
@Entity
public class ItemCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_category_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Long categoryId;

    @Builder
    public ItemCategory(Long categoryId) {
        if (categoryId == null) throw new InvalidParamException("ProductCategory.categoryid");

        this.categoryId = categoryId;
    }
}
