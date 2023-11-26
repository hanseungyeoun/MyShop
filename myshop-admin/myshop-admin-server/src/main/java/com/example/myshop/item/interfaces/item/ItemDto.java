package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.infrastructure.item.ItemStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterItemRequest {
        @NotBlank
        private String itemName;

        @NotNull
        @PositiveOrZero
        private Integer itemPrice;

        @NotBlank
        private String itemDetails;

        @NotNull
        private List<Long> categoryIds;

        @NotNull
        private List<RegisterItemOptionGroupRequest> itemOptionGroups;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterItemOptionGroupRequest {
        @NotNull
        private Integer ordering;
        @NotBlank
        private String itemOptionGroupName;
        @NotNull
        private List<RegisterItemOptionRequest> itemOptions;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterItemOptionRequest {
        @NotNull
        private Integer ordering;

        @NotBlank
        private String itemOptionName;
    }

    @Getter
    @Setter
    @ToString
    public static class RegisterItemResponse {
        private Long id;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateItemRequest {
        @NotBlank
        private String itemName;

        @NotNull
        @PositiveOrZero
        private Integer itemPrice;

        @NotBlank
        private String itemDetails;

        @NotNull
        private ItemStatus status;

        @NotNull
        private List<Long> categoryIds;

        @NotNull
        private List<UpdateItemOptionGroupRequest> itemOptionGroups;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateItemOptionGroupRequest {
        private Integer ordering;
        private String itemOptionGroupName;
        private List<RegisterItemOptionRequest> itemOptions;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateItemOptionRequest {
        private Integer ordering;
        private String itemOptionName;
    }

}
