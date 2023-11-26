package com.example.myshop.item.interfaces.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemResponseDto {

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Main {
        private final Long id;
        private final String itemName;
        private final Integer itemPrice;
        private final String itemDetails;
        private final String itemStatus;
        private final String itemStatusDescription;
        private final List<String> itemImages;
        private final List<String> categoryNames;
        private final List<ItemInfo.CouponInfo> couponInfos;
        private final List<ItemInfo.CategoryInfo> categoryInfos;
        private final List<ItemInfo.ItemOptionGroupInfo> itemOptionGroups;
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ItemOptionGroupInfo {
        private final Integer ordering;
        private final String itemOptionGroupName;
        private final List<ItemInfo.ItemOptionInfo> itemOptions;
    }

    @Builder
    @Getter
    @ToString
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class ItemOptionInfo {
        private final Integer ordering;
        private final String itemOptionName;
    }

}
