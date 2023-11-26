package com.example.myshop.item.application.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.dto.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemQueryService {

    ItemInfo.Main retrieveItem(Long itemId);

    Page<ItemInfo.Main> retrieveItems(ItemSearchCondition cond, Pageable pageable);
}
