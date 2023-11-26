package com.example.myshop.item.interfaces.category;

import com.example.myshop.item.domain.category.CategoryInfo;
import com.example.myshop.item.application.category.CategoryQueryService;
import com.example.myshop.response.APIDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryQueryController {

  private final CategoryQueryService categoryQueryService;
  private final CategoryResponseMapper mapper;

  @GetMapping("/{rootPath}")
  public APIDataResponse<List<CategoryResponse>> retrieveCategories(@PathVariable String rootPath) {
    List<CategoryInfo> categoryResult = categoryQueryService.retrieveCategories(rootPath);
    List<CategoryResponse> response = categoryResult.stream().map(mapper::of).toList();
    return APIDataResponse.success(response);
  }
}
