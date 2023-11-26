package com.example.myshop.item.infrastructure.item;

import com.example.myshop.item.domain.item.ItemOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemOptionRepository extends JpaRepository<ItemOption, Long> {

    void deleteAllByItemOptionGroup_Id(Long id);
}