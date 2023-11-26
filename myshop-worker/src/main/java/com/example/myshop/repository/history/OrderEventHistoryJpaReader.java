package com.example.myshop.repository.history;

import com.example.myshop.domain.OrderEventHistoryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventHistoryJpaReader implements OrderEventHistoryReader {

    private final OrderEventHistoryJpaRepository repository;

    @Override
    public boolean existsByTxId(String txId) {
        return repository.existsByTxId(txId);
    }
}
