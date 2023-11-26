package com.example.myshop.order.application;

import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InventoryFacade {

    private final InventoryReader inventoryReader;
    private final OrderReader orderReader;
    private final InventoryService inventoryService;


    public void increaseQuantityByOrderId(Long orderId) {
        Order order = orderReader.getOrder(orderId);
        Map<Long, List<OptionGroup>> optionGroupMap = order.convertToOptionGroupMap();
        Map<Long, Integer> itemCountMap = order.getOrdeItemrCountMap();
        increaseQuantity(optionGroupMap, itemCountMap);
    }

    private void increaseQuantity(Map<Long, List<OptionGroup>> optionGroupMap, Map<Long, Integer> itemtCountMap) {
        for (Long  itemId : optionGroupMap.keySet()) {
            Inventory inventory = inventoryReader.getAllByItemIdAndOptionGroupIn(itemId, optionGroupMap.get(itemId));
            inventoryService.increaseQuantity(inventory.getId(), itemtCountMap.get(itemId));
        }
    }

    public void decreaseQuantity(Map<Long, List<OptionGroup>> optionGroupMap, Map<Long, Integer> itemtCountMap)  {
        for (Long  itemId : optionGroupMap.keySet()) {
            Inventory inventory = inventoryReader.getAllByItemIdAndOptionGroupIn(itemId, optionGroupMap.get(itemId));
            inventoryService.decreaseQuantity(inventory.getId(), itemtCountMap.get(itemId));
        }
    }

    public void decreaseQuantityByOrderId(Long orderId)  {
        Order order = orderReader.getOrder(orderId);
        Map<Long, List<OptionGroup>> optionGroupMap = order.convertToOptionGroupMap();
        Map<Long, Integer> itemCountMap = order.getOrdeItemrCountMap();
        decreaseQuantity(optionGroupMap, itemCountMap);
    }
}
