package com.example.myshop.service.inventory;

import com.example.myshop.annotation.DistributedLock;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IncreaseQuantityInventoryServiceImpl implements IncreaseQuantityInventoryService {

    private final InventoryReader inventoryReader;

    @Override
    @DistributedLock(key = "#inventoryId")
    public void increaseQuantity(Long inventoryId, Integer quantity) {
        Inventory inventory = inventoryReader.getInventory(inventoryId);
        inventory.increaseQuantity(quantity);
    }
}
