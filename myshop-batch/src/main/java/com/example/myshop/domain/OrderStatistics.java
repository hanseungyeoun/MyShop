package com.example.myshop.domain;

import com.example.myshop.order.domain.Order;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orderStatistics")
public class OrderStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer amount;
    private Integer count;
    private LocalDate date;

    @Builder
    public OrderStatistics(Integer amount, Integer count, String date) {
        this.amount = amount;
        this.count = count;
        this.date = LocalDate.parse(date);
    }

    public void change(Integer calculateTotalAmount, Integer calculateTotalCount) {
        this.amount = calculateTotalAmount;
        this.count = calculateTotalCount;
    }
}

