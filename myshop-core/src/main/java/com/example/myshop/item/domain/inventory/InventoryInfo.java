package com.example.myshop.item.domain.inventory;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class InventoryInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final Integer quantity;
        private final Long itemId;
        private final String itemName;
        private List<InventoryOptionGroupInfo> inventoryItemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    public static class InventoryOptionGroupInfo {

        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<InventoryOptionInfo> inventoryItemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class InventoryOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
    }
}
