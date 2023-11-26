package com.example.myshop.order.infrastructure;

import com.example.myshop.exception.EntityNotFoundException;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.dto.OrderSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderJpaReader implements OrderReader {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order getOrder(Long orderId) {
        return orderJpaRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("order id not found %d", orderId)));
    }

    @Override
    public Optional<Order> findById(Long orderId) {
        return orderJpaRepository.findById(orderId);
    }

    @Override
    public Page<OrderInfo.Main> findOrderAll(OrderSearchCondition cond, Long memberId, Pageable pageable) {
        return orderJpaRepository.findOrderAll(cond, memberId, pageable);
    }

    @Override
    public List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to){
        return orderJpaRepository.findByCreatedAtBetween(from, to);
    }

}
