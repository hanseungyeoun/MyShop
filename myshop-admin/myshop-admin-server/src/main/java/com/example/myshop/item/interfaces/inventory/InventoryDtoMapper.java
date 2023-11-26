package com.example.myshop.item.interfaces.inventory;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import static com.example.myshop.item.application.inventory.InventoryCommand.*;
import static com.example.myshop.item.interfaces.inventory.InventoryDto.*;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface InventoryDtoMapper {

    RegisterInventoryCommand of(RegisterInventoryRequest request);
    RegisterInventoryResponse of(Long id);
}
