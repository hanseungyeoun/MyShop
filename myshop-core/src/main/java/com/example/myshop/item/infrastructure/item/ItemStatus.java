package com.example.myshop.item.infrastructure.item;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ItemStatus {

    ON_PREPARE("판매준비중"),
    ON_SALE("판매중"),
    END_OF_SALE("품절");
    private final String description;

    @JsonCreator
    public static ItemStatus from(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.getDescription().equals(requestValue)
                        || v.toString().equalsIgnoreCase(requestValue) )
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
