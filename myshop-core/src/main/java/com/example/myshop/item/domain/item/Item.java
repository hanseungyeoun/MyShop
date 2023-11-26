package com.example.myshop.item.domain.item;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.MoneyConverter;
import com.example.myshop.item.infrastructure.item.ItemStatus;
import com.example.myshop.exception.InvalidParamException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "items")
@ToString(callSuper = true)
@Entity
public class Item extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    @Embedded
    @Convert(attributeName = "item_price", converter = MoneyConverter.class)
    @AttributeOverride(name = "money", column = @Column(name = "item_price"))
    private Money itemPrice = Money.ZERO;

    @Lob
    private String itemDetails;

    private Long adminId;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ItemImage> itemImages = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("category_id asc")
    private final List<ItemCategory> itemCategories = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordering asc")
    private final List<ItemOptionGroup> itemOptionGroups = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    //==생성 메서드==//
    @Builder
    public Item(String itemName, Money itemPrice, String itemDetails, List<ItemImage> itemImages, List<ItemCategory> itemCategories, List<ItemOptionGroup> optionGroups) {
        if (StringUtils.isEmpty(itemName)) throw new InvalidParamException("item.itemName");
        if (itemPrice == null) throw new InvalidParamException("item.itemPrice");
        if (StringUtils.isEmpty(itemDetails)) throw new InvalidParamException("item.itemDetail");
        if (itemImages == null) throw new InvalidParamException("item.image");
        if (itemCategories == null) throw new InvalidParamException("item.itemCategories");
        if (optionGroups == null) throw new InvalidParamException("item.optionGroups");

        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDetails = itemDetails;
        this.status = ItemStatus.ON_PREPARE;

        addImages(itemImages);
        addCategorys(itemCategories);
        addItemOptionGroups(optionGroups);
    }

    public void addImages(List<ItemImage> itemImages) {
        for (ItemImage itemImage : itemImages) {
            addImage(itemImage);
        }
    }

    public void addImage(ItemImage itemImage) {
        this.itemImages.add(itemImage);
        itemImage.setItem(this);
    }

    public void addCategorys(List<ItemCategory> itemCategories) {
        for (ItemCategory itemCategory : itemCategories) {
            addCategory(itemCategory);
        }
    }

    public void addCategory(ItemCategory itemCategory) {
        this.itemCategories.add(itemCategory);
        itemCategory.setItem(this);
    }

    public void addItemOptionGroups(List<ItemOptionGroup> optionGroups) {
        for (ItemOptionGroup optionGroup : optionGroups) {
            addItemOptionGroup(optionGroup);
        }
    }

    public void addItemOptionGroup(ItemOptionGroup optionGroup) {
        this.itemOptionGroups.add(optionGroup);
        optionGroup.setItem(this);
    }

    //==비즈니스 로직==//
    public void changeItem(String itemName, Money itemPrice, String itemDetails, ItemStatus status) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemDetails = itemDetails;
        this.status = status;
    }

    public void clearItemSeries() {
        clearItemOptionGroup();
        clearItemImage();
        clearCategories();
    }

    public void changeOnSale() {
        this.status = ItemStatus.ON_SALE;
    }

    public void changeEndOfSale() {
        this.status = ItemStatus.END_OF_SALE;
    }

    public boolean isOnSale() {
        return this.status == ItemStatus.ON_SALE;
    }

    private void clearItemOptionGroup() {
        itemOptionGroups.forEach(ItemOptionGroup::clearOption);
        itemOptionGroups.clear();
    }

    private void clearItemImage() {
        itemImages.clear();
    }

    private void clearCategories() {
        this.itemCategories.clear();
    }
}

