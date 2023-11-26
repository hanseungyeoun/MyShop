package com.example.myshop.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OptionGroup {

    private Integer ordering;
    private String itemOptionGroupName;
    List<Option> options;
}
