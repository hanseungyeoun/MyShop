package com.example.myshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ItemSearchType {
    ITEM_NAME("상품명"),
    CATEGORY_PATH("zzz"),
    ITEM_ID("상품 ID");

    private final String description;
}