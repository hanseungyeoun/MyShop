package com.example.myshop.item.application.inventory;

import com.example.myshop.annotation.DistributedLock;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import com.example.myshop.order.application.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryReader inventoryReader;

    @Override
    @DistributedLock(key = "#inventoryId")
    public void increaseQuantity(Long inventoryId, Integer quantity) {
        Inventory inventory = inventoryReader.getInventory(inventoryId);
        inventory.increaseQuantity(quantity);
    }

    @Override
    @DistributedLock(key = "#inventoryId")
    public void decreaseQuantity(Long inventoryId, Integer quantity){
        Inventory inventory = inventoryReader.getInventory(inventoryId);
        inventory.decreaseQuantity(quantity);
    }
}
