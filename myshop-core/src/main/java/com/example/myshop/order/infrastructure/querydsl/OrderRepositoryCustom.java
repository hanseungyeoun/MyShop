package com.example.myshop.order.infrastructure.querydsl;

import com.example.myshop.order.dto.OrderSearchCondition;
import com.example.myshop.order.domain.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderRepositoryCustom {

    Page<OrderInfo.Main> findOrderAll(OrderSearchCondition cond, Long memberId, Pageable pageable);
}
