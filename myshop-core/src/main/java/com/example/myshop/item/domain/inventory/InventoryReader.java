package com.example.myshop.item.domain.inventory;

import com.example.myshop.order.dto.OptionGroup;

import java.util.List;

public interface InventoryReader {

    Inventory getInventory(Long inventoryId);
    List<Inventory> getInventoryByItemId(Long itemId);
    Inventory getAllByItemIdAndOptionGroupIn(Long itemId, List<OptionGroup> optionGroup);
}
