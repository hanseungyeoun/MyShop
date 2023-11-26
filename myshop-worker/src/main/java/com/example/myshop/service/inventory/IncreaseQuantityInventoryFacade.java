package com.example.myshop.service.inventory;

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
public class IncreaseQuantityInventoryFacade {

    private final InventoryReader inventoryReader;
    private final OrderReader orderReader;
    private final IncreaseQuantityInventoryService changeQuantityInventoryService;

    public void increaseQuantityByOrderId(Long orderId) {
        Order order = orderReader.getOrder(orderId);
        Map<Long, List<OptionGroup>> optionGroupMap = order.convertToOptionGroupMap();
        Map<Long, Integer> itemCountMap = order.getOrdeItemrCountMap();
        increaseQuantity(optionGroupMap, itemCountMap);
    }

    private void increaseQuantity(Map<Long, List<OptionGroup>> optionGroupMap, Map<Long, Integer> itemtCountMap) {
        for (Long  itemId : optionGroupMap.keySet()) {
            Inventory inventory = inventoryReader.getAllByItemIdAndOptionGroupIn(itemId, optionGroupMap.get(itemId));
            changeQuantityInventoryService.increaseQuantity(inventory.getId(), itemtCountMap.get(itemId));
        }
    }
}
