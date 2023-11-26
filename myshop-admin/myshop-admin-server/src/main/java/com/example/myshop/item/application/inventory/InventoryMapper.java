package com.example.myshop.item.application.inventory;

import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryOption;
import com.example.myshop.item.domain.inventory.InventoryOptionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class InventoryMapper {

    public Inventory mapFrom(InventoryCommand.RegisterInventoryCommand command) {
        return Inventory.builder()
                .quantity(command.getQuantity())
                .itemId(command.getItemId())
                .inventoryItemOptionGroups(command.getInventoryItemOptionGroups().stream().map(this::crateStockItemGroupOption).toList())
                .build();
    }

    private InventoryOptionGroup crateStockItemGroupOption(InventoryCommand.RegisterInventoryOptionGroupCommand stockOptionGroup) {
        return InventoryOptionGroup.builder()
                .itemOptionGroupName(stockOptionGroup.getItemOptionGroupName())
                .ordering(stockOptionGroup.getOrdering())
                .inventoryOptions(stockOptionGroup.getInventoryItemOptions().stream().map(this::createStockOption).toList())
                .build();    
    }

    private InventoryOption createStockOption(InventoryCommand.RegisterInventoryOptionCommand stockItemOption) {
        return InventoryOption.builder()
                .ordering(stockItemOption.getOrdering())
                .itemOptionName(stockItemOption.getItemOptionName())
                .build();
    }
}
