package com.example.myshop.item.infrastructure.inventory;

import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class InventoryJpaReader implements InventoryReader {

    private final InventoryJpaRepository inventoryJpaRepository;

    @Override
    public Inventory getInventory(Long inventoryId) {
        return inventoryJpaRepository.findById(inventoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Inventory id not found %d", inventoryId)));
    }

    @Override
    public List<Inventory> getInventoryByItemId(Long itemId) {
        return inventoryJpaRepository.findByItemId(itemId);
    }

    @Override
    public Inventory getAllByItemIdAndOptionGroupIn(Long itemId, List<OptionGroup> optionGroup) {
        return inventoryJpaRepository.findAllByItemIdAndOptionGroupIn(itemId, optionGroup)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Inventory id not found %d", itemId)));
    }
}
