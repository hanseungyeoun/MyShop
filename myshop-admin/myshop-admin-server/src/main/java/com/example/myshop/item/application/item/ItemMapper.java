package com.example.myshop.item.application.item;


import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemImage;
import com.example.myshop.item.domain.item.ItemCategory;
import com.example.myshop.item.domain.item.ItemOption;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import com.example.myshop.item.infrastructure.item.FileUploaderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@Component
public class ItemMapper {

    private final FileUploaderImpl fileUploder;

    public Item mapFrom(ItemCommand.RegisterItemCommand item) {
        return Item.builder()
                .itemName(item.getItemName())
                .itemPrice(item.getItemPrice())
                .itemDetails(item.getItemDetails())
                .itemImages(fromImage(item.getItemImages()))
                .itemCategories(fromCategoryId(item.getCategoryIds()))
                .optionGroups(item.getItemOptionGroups()
                        .stream()
                        .map(this::toItemOptionGroup)
                        .toList())
                .build();
    }

    public List<ItemCategory> fromCategoryId(List<Long> categoryIds) {
        return categoryIds.stream().map(ItemCategory::new).toList();
    }

    public List<ItemImage> fromImage(List<MultipartFile> images) {
        List<ItemImage> itemImages;

        try {
            itemImages = fileUploder.storeFiles(images).stream().map(ItemImage::new).toList();
        } catch (IOException e) {
            throw new IllegalStatusException();
        }
        return itemImages;
    }

    public List<ItemOptionGroup> fromUpdateItemOptionGroups(List<ItemCommand.UpdateItemOptionGroupCommand> commands) {
        return commands.stream().map(this::toItemOptionGroup).toList();
    }

    private ItemOptionGroup toItemOptionGroup(ItemCommand.RegisterItemOptionGroupCommand command) {
        return new ItemOptionGroup(command.getOrdering(),
                command.getItemOptionGroupName(),
                command.getItemOptions().stream()
                        .map(this::toItemOption)
                        .toList());
    }

    private ItemOption toItemOption(ItemCommand.RegisterItemOptionCommand itemOption) {
        return new ItemOption(itemOption.getOrdering(), itemOption.getItemOptionName());
    }


    private ItemOptionGroup toItemOptionGroup(ItemCommand.UpdateItemOptionGroupCommand itemOptionGroup) {
        return new ItemOptionGroup(itemOptionGroup.getOrdering(),
                itemOptionGroup.getItemOptionGroupName(),
                itemOptionGroup.getItemOptions().stream()
                        .map(this::toItemOption)
                        .toList());
    }

    private ItemOption toItemOption(ItemCommand.UpdateItemOptionCommand command) {
        return new ItemOption(command.getOrdering(), command.getItemOptionName());
    }
}
