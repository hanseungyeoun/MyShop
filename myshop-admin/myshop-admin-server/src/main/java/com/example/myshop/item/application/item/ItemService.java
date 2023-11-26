package com.example.myshop.item.application.item;

import static com.example.myshop.item.application.item.ItemCommand.*;

public interface ItemService {

    Long registerItem(RegisterItemCommand command);

    void updateItem(Long itemId, UpdateItemCommand command);

    void changeOnSaleItem(Long itemId);

    void changeEndOfSaleItem(Long itemId);
}