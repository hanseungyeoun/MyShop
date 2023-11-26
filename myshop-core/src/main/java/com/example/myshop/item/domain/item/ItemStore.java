package com.example.myshop.item.domain.item;

public interface ItemStore {

    Item store(Item item);

    void flush();
}
