package com.example.myshop.repository;

import com.example.myshop.domain.OrderStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderStatisticsRepository extends JpaRepository<OrderStatistics, Long> {

    Optional<OrderStatistics> findByDate(LocalDate date);
}
