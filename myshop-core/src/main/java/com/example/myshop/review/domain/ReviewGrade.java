package com.example.myshop.review.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum ReviewGrade {
    ONE(1), TWO(2), TREE(3), FOUR(4), FIVE(5);

    private final Integer description;

    @JsonCreator
    public static ReviewGrade from(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.getDescription().equals(requestValue)
                        || v.toString().equalsIgnoreCase(requestValue) )
                .findFirst()
                .orElse(null);
    }

    @JsonValue
    public Integer getDescription() {
        return description;
    }
}
