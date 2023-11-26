package com.example.myshop.item.infrastructure.item;

import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ItemJpaStore implements ItemStore {

    private final ItemJpaRepository itemRepository;

    @Override
    public Item store(Item item) {
        return itemRepository.save(item);
    }

    @Override
    public void flush() {
        itemRepository.flush();
    }
}
