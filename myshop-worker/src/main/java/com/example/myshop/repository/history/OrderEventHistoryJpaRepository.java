package com.example.myshop.repository.history;

import com.example.myshop.domain.OrderEventHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderEventHistoryJpaRepository extends JpaRepository<OrderEventHistory, Long> {

    boolean existsByTxId(String txId);
}