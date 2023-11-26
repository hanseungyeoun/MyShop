package com.example.myshop.item.application.inventory;

import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterInventoryCommand {
        private final Integer quantity;
        private final Long itemId;
        private final String itemName;

        private List<RegisterInventoryOptionGroupCommand> inventoryItemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterInventoryOptionGroupCommand {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterInventoryOptionCommand> inventoryItemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterInventoryOptionCommand {
        private final Integer ordering;
        private final String itemOptionName;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateInventoryCommand {
        private final Long stockId;
        private final Integer quantity;
    }
}
