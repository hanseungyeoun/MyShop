package com.example.myshop.item.domain.item;

import com.example.myshop.item.dto.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ItemReader {

    Item getItem(Long itemId);
    Optional<Item> findById(Long itemId);
    List<Item> findAllById(List<Long> itemIds);
    Page<ItemInfo.Main> findItemAll(ItemSearchCondition condition, Pageable pageable);
}