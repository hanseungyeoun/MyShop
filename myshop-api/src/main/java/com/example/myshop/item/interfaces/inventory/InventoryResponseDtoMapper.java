package com.example.myshop.item.interfaces.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InventoryResponseDtoMapper {

    InventoryResponseDto.Main of(InventoryInfo.Main mainResult);

}
