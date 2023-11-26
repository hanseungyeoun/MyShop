package com.example.myshop.common.jpa;


import jakarta.persistence.AttributeConverter;

public class RatioConverter implements AttributeConverter<Ratio, Double> {

    @Override
    public Double convertToDatabaseColumn(Ratio ratio) {
        return ratio == null ? Double.valueOf(0.0f) : ratio.getRate();
    }

    @Override
    public Ratio convertToEntityAttribute(Double value) {
        return value == null ? Ratio.ZERO : new Ratio(value);
    }
}
