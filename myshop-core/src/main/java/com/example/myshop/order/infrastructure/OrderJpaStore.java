package com.example.myshop.order.infrastructure;

import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderJpaStore implements OrderStore {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order store(Order order) {
        return orderJpaRepository.save(order);
    }
}
