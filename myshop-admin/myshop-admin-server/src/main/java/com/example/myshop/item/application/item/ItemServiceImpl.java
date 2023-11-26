package com.example.myshop.item.application.item;

import com.example.myshop.item.domain.item.*;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.myshop.item.application.item.ItemCommand.*;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemServiceImpl implements ItemService {

    private final ItemStore itemStore;
    private final ItemReader itemReader;
    private final ItemMapper itemMapper;

    @Override
    public Long registerItem(RegisterItemCommand command) {
        Item item = itemMapper.mapFrom(command);
        Item storedItem = itemStore.store(item);
        return storedItem.getId();
    }

    @Override
    public void updateItem(Long itemId, UpdateItemCommand command) {
        Item item = itemReader.getItem(itemId);
        item.changeItem(command.getItemName(), command.getItemPrice(), command.getItemDetails(), command.getStatus());
        item.clearItemSeries();

        List<ItemImage> itemImages = itemMapper.fromImage(command.getItemImages());
        List<ItemOptionGroup> itemOptionGroups = itemMapper.fromUpdateItemOptionGroups(command.getItemOptionGroups());
        List<ItemCategory> itemCategories = itemMapper.fromCategoryId(command.getCategoryIds());

        item.addItemOptionGroups(itemOptionGroups);
        item.addImages(itemImages);
        item.addCategorys(itemCategories);
    }

    @Override
    public void changeOnSaleItem(Long itemId) {
        Item item = itemReader.getItem(itemId);
        item.changeOnSale();
    }

    @Override
    public void changeEndOfSaleItem(Long itemId) {
        Item item = itemReader.getItem(itemId);
        item.changeEndOfSale();
    }
}
