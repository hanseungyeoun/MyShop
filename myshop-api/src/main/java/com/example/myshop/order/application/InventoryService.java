package com.example.myshop.order.application;

public interface InventoryService {
    void increaseQuantity(Long inventoryId, Integer quantity);
    void decreaseQuantity(Long inventoryId, Integer quantity);
}
