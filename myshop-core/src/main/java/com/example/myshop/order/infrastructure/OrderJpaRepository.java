package com.example.myshop.order.infrastructure;

import com.example.myshop.order.domain.Order;
import com.example.myshop.order.infrastructure.querydsl.OrderRepositoryCustom;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderJpaRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    List<Order> findByIdIn(List<Long> id);

    List<Order> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to);
}