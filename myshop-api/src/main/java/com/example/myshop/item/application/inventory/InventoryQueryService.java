package com.example.myshop.item.application.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;

import java.util.List;

public interface InventoryQueryService {
    List<InventoryInfo.Main> retrieveInventoryListByItemId(Long itemId);
}

