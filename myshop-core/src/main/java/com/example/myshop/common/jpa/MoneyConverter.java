package com.example.myshop.common.jpa;

import jakarta.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money == null ? Integer.valueOf(0) : money.getMoney().intValue();
    }

    @Override
    public Money convertToEntityAttribute(Integer value) {
        return value == null ? Money.ZERO : Money.valueOf(value);
    }
}
