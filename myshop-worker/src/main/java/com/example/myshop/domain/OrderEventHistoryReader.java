package com.example.myshop.domain;

public interface OrderEventHistoryReader {

    boolean existsByTxId(String txId);
}
