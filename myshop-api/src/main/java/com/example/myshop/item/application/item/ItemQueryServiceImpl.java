package com.example.myshop.item.application.item;

import com.example.myshop.item.domain.item.ItemInfo;
import com.example.myshop.item.domain.category.Category;
import com.example.myshop.item.domain.category.CategoryReader;
import com.example.myshop.item.domain.item.ItemReader;
import com.example.myshop.item.dto.ItemSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemQueryServiceImpl implements ItemQueryService {

    private final ItemReader itemReader;
    private final CategoryReader categoryReader;
    private final ItemInfoMapper itemInfoMapper;

    public ItemInfo.Main retrieveItem(Long itemId) {
        List<Category> categories = categoryReader.findByItemId(itemId);
        List<ItemInfo.CategoryInfo> categoryInfos = categories.stream().map(category -> itemInfoMapper.of(itemId, category)).toList();
        return itemInfoMapper.of(itemReader.getItem(itemId), categoryInfos);
    }

    public Page<ItemInfo.Main> retrieveItems(ItemSearchCondition cond, Pageable pageable) {
        return itemReader.findItemAll(cond, pageable);
    }
}
