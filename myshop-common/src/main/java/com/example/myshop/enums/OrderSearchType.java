package com.example.myshop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderSearchType {
    ITEM_NAME("상품명"),
    ORDER_ID("주문 번호");

    private final String description;
}
