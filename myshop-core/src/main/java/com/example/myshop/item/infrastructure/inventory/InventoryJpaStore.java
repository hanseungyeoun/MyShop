package com.example.myshop.item.infrastructure.inventory;

import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InventoryJpaStore implements InventoryStore {

    private final InventoryJpaRepository inventoryJpaRepository;

    @Override
    public Inventory store(Inventory inventory) {
        return inventoryJpaRepository.saveAndFlush(inventory);
    }

    @Override
    public void deleteById(Long inventoryId) {
        inventoryJpaRepository.deleteById(inventoryId);
    }
}
