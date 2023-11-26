package com.example.myshop.common.jpa;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Embeddable
@NoArgsConstructor
public class Ratio {

    private double rate;

    public static final Ratio ZERO = Ratio.valueOf(0);

    public static Ratio valueOf(double rate) {
        return new Ratio(rate);
    }

    Ratio(double rate) {
        this.rate = rate;
    }

    public Money of(Money price) {
        return price.times(rate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ratio ratio = (Ratio) o;
        return Double.compare(ratio.rate, rate) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate);
    }
}
