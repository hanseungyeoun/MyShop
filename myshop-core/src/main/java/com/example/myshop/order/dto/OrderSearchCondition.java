package com.example.myshop.order.dto;

import com.example.myshop.enums.OrderSearchType;
import com.example.myshop.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class OrderSearchCondition {
    private OrderSearchType searchType;
    private String searchValue;
    private OrderStatus orderStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDatetime;

    @Builder
    public OrderSearchCondition(OrderSearchType searchType, String searchValue, OrderStatus orderStatus, LocalDate startDatetime, LocalDate endDatetime) {
        this.searchType = searchType;
        this.searchValue = searchValue;
        this.orderStatus = orderStatus;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
    }
}
