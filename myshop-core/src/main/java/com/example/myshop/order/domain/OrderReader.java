package com.example.myshop.order.domain;


import com.example.myshop.order.dto.OrderSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderReader {

    Order getOrder(Long orderId);

    Optional<Order> findById(Long orderId);

    Page<OrderInfo.Main> findOrderAll(OrderSearchCondition condition, Long memberId, Pageable pageable);

    List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}