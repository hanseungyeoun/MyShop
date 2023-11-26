package com.example.myshop.item.infrastructure.item;

import com.example.myshop.item.infrastructure.item.querydsql.ItemRepositoryCustom;
import com.example.myshop.item.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemJpaRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {
}