package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.dto.ItemSearchCondition;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemResponseDtoMapper {
    @Mapping(source = "itemId", target = "id")
    ItemResponseDto.Main of(ItemInfo.Main mainResult);

    ItemSearchCondition of(ItemSearchCondition condition);
}
