package com.example.myshop.repository.history;

import com.example.myshop.domain.OrderEventHistory;
import com.example.myshop.domain.OrderEventHistoryStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderEventHistoryJpaStore implements OrderEventHistoryStore {

    private final OrderEventHistoryJpaRepository repository;

    @Override
    public OrderEventHistory store(OrderEventHistory history) {
        return repository.save(history);
    }
}
