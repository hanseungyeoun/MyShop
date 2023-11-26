package com.example.myshop.item.interfaces.category;


import com.example.myshop.item.domain.category.CategoryInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryResponseMapper {
  CategoryResponse of(CategoryInfo info);
}
