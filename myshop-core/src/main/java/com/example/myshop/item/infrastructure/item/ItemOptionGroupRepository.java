package com.example.myshop.item.infrastructure.item;

import com.example.myshop.item.domain.item.ItemOptionGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemOptionGroupRepository extends JpaRepository<ItemOptionGroup, Long> {
    void deleteAllByItem_Id(Long itemId);

    @Modifying
    @Query("delete from ItemOptionGroup i where i.item.id = :itemId")
    void deleteAllByItemId(@Param("itemId") Long itemId);
}