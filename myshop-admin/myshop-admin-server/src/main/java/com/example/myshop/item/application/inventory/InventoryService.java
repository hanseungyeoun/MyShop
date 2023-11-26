package com.example.myshop.item.application.inventory;

import static com.example.myshop.item.application.inventory.InventoryCommand.RegisterInventoryCommand;

public interface InventoryService {

    Long registerInventory(RegisterInventoryCommand command);

    void updateQuantity(Long inventoryId, Integer quantity);

    void deleteInventory(Long inventoryId);
}
