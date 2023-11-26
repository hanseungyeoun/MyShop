package com.example.myshop.item.application.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemImage;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ItemInfoMapper {

    @Mapping(source = "item.id", target = "itemId")
    @Mapping(source = "item.itemImages", target = "itemImages", qualifiedByName = "itemImageToStringList")
    @Mapping(source = "item.itemPrice", target = "itemPrice", qualifiedByName = "moneyToInteger")
    @Mapping(expression = "java(item.getStatus().name())", target = "itemStatus")
    @Mapping(expression = "java(item.getStatus().getDescription())", target = "itemStatusDescription")
    ItemInfo.Main of(Item item, List<ItemInfo.CategoryInfo> categoryInfos);

    @Mapping(source = "item.id", target = "itemId")

    ItemInfo.ItemOptionGroupInfo of(ItemOptionGroup itemOptionGroup);
    ItemInfo.CategoryInfo of(Long itemId, Category category);

    @Named("moneyToInteger")
    static Integer moneyToInteger(Money itemPrice) {
        return itemPrice.getMoney().intValue();
    }

    @Named("itemImageToStringList")
    static List<String> itemImageToStringList(List<ItemImage> images) {
        return images == null ? new ArrayList<>() : images.stream().map(ItemImage::getImagePath).toList();
    }
}
