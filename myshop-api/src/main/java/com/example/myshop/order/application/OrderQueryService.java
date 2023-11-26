package com.example.myshop.order.application;

import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.dto.OrderSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderQueryService {

    Page<OrderInfo.Main> retrieveOrders(OrderSearchCondition cond, Long id, Pageable pageable);

    OrderInfo.Main retrieveOrder(Long orderId);
}
