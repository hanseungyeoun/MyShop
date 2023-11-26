package com.example.myshop.item.application.inventory;

import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import com.example.myshop.item.domain.inventory.InventoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.myshop.item.application.inventory.InventoryCommand.RegisterInventoryCommand;

@RequiredArgsConstructor
@Service
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryStore inventoryStore;
    private final InventoryReader inventoryReader;
    private final InventoryMapper inventoryMapper;

    @Override
    public Long registerInventory(RegisterInventoryCommand stock) {
        //TODO ITEM ID 예외 처리 옵션 중복 방지 예외 처리
        Inventory inventory = inventoryMapper.mapFrom(stock);
        Inventory storedInventory = inventoryStore.store(inventory);
        return storedInventory.getId();
    }

    @Override
    public void updateQuantity(Long inventoryId, Integer quantity) {
        Inventory inventory = inventoryReader.getInventory(inventoryId);
        inventory.changeQuantity(quantity);
    }

    @Override
    public void deleteInventory(Long inventoryId) {
        inventoryStore.deleteById(inventoryId);
    }
}
