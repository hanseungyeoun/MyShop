package com.example.myshop.item.infrastructure.inventory.querydsl;

import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.item.domain.inventory.Inventory;

import java.util.List;
import java.util.Optional;

public interface InventoryRepositoryCustom {

    Optional<Inventory> findAllByItemIdAndOptionGroupIn(Long itemId, List<OptionGroup> optionGroup);
}
