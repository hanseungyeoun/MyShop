package com.example.myshop.item.infrastructure.item;

import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.dto.ItemSearchCondition;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemReader;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ItemJpaReader implements ItemReader {

    private final ItemJpaRepository itemRepository;

    @Override
    public Item getItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("item id not found %d", itemId)));
    }

    @Override
    public Optional<Item> findById(Long itemId) {
        return itemRepository.findById(itemId);
    }

    @Override
    public List<Item> findAllById(List<Long> itemIds) {
        return itemRepository.findAllById(itemIds);
    }

    @Override
    public Page<ItemInfo.Main> findItemAll(ItemSearchCondition cond, Pageable pageable) {
        return itemRepository.findItemAll(cond, pageable);
    }
}
