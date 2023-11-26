package com.example.myshop.item.interfaces.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class UpdateCategoryNameRequest {
    private String newCategoryName;
}
