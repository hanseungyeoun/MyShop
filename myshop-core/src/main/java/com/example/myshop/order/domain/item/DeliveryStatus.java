package com.example.myshop.order.domain.item;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStatus {

    BEFORE_DELIVERY("배송전"),
    DELIVERY_PREPARE("배송준비중"),
    DELIVERING("배송중"),
    COMPLETE_DELIVERY("배송완료");

    private final String description;
}