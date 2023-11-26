package com.example.myshop.domain;

public interface OrderEventHistoryStore {

    OrderEventHistory store(OrderEventHistory history);
}
