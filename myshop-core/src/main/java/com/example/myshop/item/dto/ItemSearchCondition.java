package com.example.myshop.item.dto;

import com.example.myshop.enums.ItemSearchType;
import com.example.myshop.item.infrastructure.item.ItemStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
@Getter
@ToString
public class ItemSearchCondition {
    private ItemSearchType searchType;
    private String searchValue;
    private ItemStatus itemStatus;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDatetime;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDatetime;
}
