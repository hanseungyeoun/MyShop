package com.example.myshop.common.jpa;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Embeddable
@NoArgsConstructor
public class Money {

    public static final Money ZERO = Money.valueOf(0);

    private BigDecimal money;

    public static Money valueOf(int value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money valueOf(double value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money valueOf(long value) {
        return new Money(BigDecimal.valueOf(value));
    }

    public static Money clone(Money value) {
        return new Money(value.money);
    }


    private Money(BigDecimal money) {
        this.money = money;
    }

    public static <T> Money sum(Collection<T> bags, Function<T, Money> monetary) {
        return bags.stream().map(bag -> monetary.apply(bag)).reduce(Money.ZERO, Money::plus);
    }

    public static <T> Money discount(Collection<T> bags, Function<T, Money> monetary) {
        return bags.stream().map(bag -> monetary.apply(bag)).reduce(Money.ZERO, Money::minus);
    }

    public Money plus(Money amount) {
        return new Money(this.money.add(amount.money));
    }

    public Money minus(Money amount) {
        return new Money(this.money.subtract(amount.money));
    }

    public Money times(double percent) {
        return new Money(this.money.multiply(BigDecimal.valueOf(percent)));
    }

    public Money divide(double divisor) {
        return new Money(this.money.divide(BigDecimal.valueOf(divisor)));
    }

    public boolean isLessThan(Money other) {
        return money.compareTo(other.money) < 0;
    }

    public boolean isGreaterThanOrEqual(Money other) {
        return money.compareTo(other.money) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return Objects.equals(this.money.intValue(), money.money.intValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }

    @Override
    public String toString() {
        return money.toString() + "원";
    }
}