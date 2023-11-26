package com.example.myshop.item.infrastructure.inventory;

import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.infrastructure.inventory.querydsl.InventoryRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryJpaRepository extends JpaRepository<Inventory, Long>, InventoryRepositoryCustom {
    List<Inventory> findByItemId(Long itemId);
}