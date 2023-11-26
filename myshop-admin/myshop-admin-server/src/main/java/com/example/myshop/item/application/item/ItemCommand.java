package com.example.myshop.item.application.item;

import com.example.myshop.common.jpa.Money;
import com.example.myshop.item.infrastructure.item.ItemStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterItemCommand {
        private final String itemName;
        private final Money itemPrice;
        private final String itemDetails;
        private final List<Long> categoryIds;
        private List<MultipartFile> itemImages;
        private final List<RegisterItemOptionGroupCommand> itemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterItemOptionGroupCommand {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<RegisterItemOptionCommand> itemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterItemOptionCommand {
        private final Integer ordering;
        private final String itemOptionName;
    }


    @Getter
    @Builder
    @ToString
    public static class UpdateItemCommand {
        private final String itemName;
        private final Money itemPrice;
        private final String itemDetails;
        private final List<Long> categoryIds;
        private final ItemStatus status;
        private final List<MultipartFile> itemImages;
        private final List<UpdateItemOptionGroupCommand> itemOptionGroups;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateItemOptionGroupCommand {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<UpdateItemOptionCommand> itemOptions;
    }

    @Getter
    @Builder
    @ToString
    public static class UpdateItemOptionCommand {
        private final Integer ordering;
        private final String itemOptionName;
    }
}
