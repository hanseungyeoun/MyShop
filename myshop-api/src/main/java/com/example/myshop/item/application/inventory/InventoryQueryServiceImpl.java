package com.example.myshop.item.application.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;
import com.example.myshop.item.domain.inventory.InventoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InventoryQueryServiceImpl implements InventoryQueryService {

    private final InventoryReader inventoryReader;
    private final InventoryInfoMapper inventoryInfoMapper;

    @Override
    public List<InventoryInfo.Main> retrieveInventoryListByItemId(Long itemId) {
        return inventoryReader.getInventoryByItemId(itemId)
                .stream().map(inventoryInfoMapper::of).toList();
    }
}
