package com.example.myshop.item.interfaces.inventory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InventoryDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterInventoryRequest {
        @NotNull
        private Integer quantity;
        @NotNull
        private Long itemId;
        @NotNull
        private List<RegisterInventoryOptionGroupRequest> inventoryItemOptionGroups;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterInventoryOptionGroupRequest {
        @NotNull
        private Integer ordering;
        @NotBlank
        private String itemOptionGroupName;
        @NotNull
        private List<RegisterInventoryOptionRequest> inventoryItemOptions;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterInventoryOptionRequest {
        @NotNull
        private Integer ordering;
        @NotBlank
        private String itemOptionName;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterInventoryResponse {
        private Long id;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateInventoryRequest {
        @NotNull
        private Integer quantity;
    }
}
