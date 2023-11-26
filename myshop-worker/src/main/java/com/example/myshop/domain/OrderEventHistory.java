package com.example.myshop.domain;

import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_event_history")
@Entity
public class OrderEventHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_history_id")
    private Long id;

    @Column(unique = true)
    private String txId;

    private Long orderId;

    private OrderEventType type;

    @Builder
    public OrderEventHistory(Long orderId, String txId, OrderEventType type) {
        if (orderId == null) throw new InvalidParamException("OrderEventHistory.orderId");
        if (txId == null) throw new InvalidParamException("OrderEventHistory.txId");
        if (type == null) throw new InvalidParamException("OrderEventHistory.type");

        this.orderId = orderId;
        this.txId = txId;
        this.type = type;
    }
}
