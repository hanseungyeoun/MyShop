package com.example.myshop.member.domain;

import lombok.Getter;

@Getter
public enum MemberGrade {
    BRONZE("Bronze", 0.1),
    SILVER("Silver", 0.2),
    GOLD("Gold", 0.3),
    VIP("VIP", 0.5);

    private final String description;
    private final double discountRate;

    MemberGrade(String description, double discountRate) {
        this.description = description;
        this.discountRate = discountRate;
    }
}
