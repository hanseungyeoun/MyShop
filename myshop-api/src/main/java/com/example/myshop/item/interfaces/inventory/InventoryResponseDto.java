package com.example.myshop.item.interfaces.inventory;

import com.example.myshop.item.domain.inventory.InventoryInfo;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryResponseDto {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long id;
        private final Integer stockCount;
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
        private final List<InventoryInfo.InventoryOptionInfo> inventoryItemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class InventoryOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
    }
}
