package com.example.myshop.item.interfaces.category;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class CategoryResponse {
  private Long id;
  private Long parentCategoryId;
  private String path;
  private String name;
  private LocalDateTime createdAt;
  private boolean hasPrevious;
  private List<CategoryResponse> childCategories = new ArrayList<>();
}
