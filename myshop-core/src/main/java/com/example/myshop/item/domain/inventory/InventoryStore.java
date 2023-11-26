package com.example.myshop.item.domain.inventory;

public interface InventoryStore {

    Inventory store(Inventory inventory);
    void deleteById(Long inventoryId);
}
