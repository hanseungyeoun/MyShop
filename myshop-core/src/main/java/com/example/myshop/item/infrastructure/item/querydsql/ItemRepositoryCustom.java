package com.example.myshop.item.infrastructure.item.querydsql;


import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.dto.ItemSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<ItemInfo.Main> findItemAll(ItemSearchCondition cond, Pageable pageable);
}