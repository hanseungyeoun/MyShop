package com.example.myshop.order.application;

import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.domain.OrderReader;
import com.example.myshop.order.dto.OrderSearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    private final OrderReader orderReader;
    private final OrderInfoMapper mapper;

    @Override
    public Page<OrderInfo.Main> retrieveOrders(OrderSearchCondition cond, Long memberId, Pageable pageable) {
        return orderReader.findOrderAll(cond, memberId, pageable);
    }

    @Override
    public OrderInfo.Main retrieveOrder(Long orderId) {
        return mapper.of(orderReader.getOrder(orderId));
    }
}
