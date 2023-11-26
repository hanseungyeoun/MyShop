package com.example.myshop.item.domain.inventory;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory")
@Entity
public class Inventory extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Long id;

    private Integer quantity;
    private Long itemId;

    private Long adminId;


    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory", cascade = CascadeType.ALL)
    @OrderBy("ordering asc")
    private final List<InventoryOptionGroup> inventoryItemOptionGroups = new ArrayList<>();

    //==생성 메서드==//
    @Builder
    public Inventory(Integer quantity, Long itemId, List<InventoryOptionGroup> inventoryItemOptionGroups) {
        if (quantity == null) throw new InvalidParamException("Inventory.orderCount");
       // if (adminId == null) throw new InvalidParamException("Inventory.adminId");
        if (inventoryItemOptionGroups == null) throw new InvalidParamException("Inventory.orderItemOptionGroups");

        this.quantity = quantity;
        this.itemId = itemId;
        addGroups(inventoryItemOptionGroups);
    }

    private void addGroups(List<InventoryOptionGroup> inventoryOptionGroups) {
        for (InventoryOptionGroup inventoryOptionGroup : inventoryOptionGroups) {
            addGroup(inventoryOptionGroup);
        }
    }

    private void addGroup(InventoryOptionGroup inventoryOptionGroup) {
        this.inventoryItemOptionGroups.add(inventoryOptionGroup);
        inventoryOptionGroup.setInventory(this);
    }

    //==비즈니스 로직==//
    public void changeQuantity(Integer stockCount) {
        verifyQuantity(stockCount);
        this.quantity = stockCount;
    }

    public void decreaseQuantity(Integer count) {
        verifyNotEnoughQuantity(count);
        this.quantity -= count;
    }

    public void increaseQuantity(Integer count) {
        this.quantity += count;
    }

    private static void verifyQuantity(Integer stockCount) {
        if (stockCount < 0)
            throw new IllegalStatusException("잘 못된 재고 값 입니다. [" + stockCount + "]");
    }

    private void verifyNotEnoughQuantity(Integer count) {
        if (this.quantity - count < 0) {
            throw new IllegalStatusException("재고가 없습니다.");
        }
    }
}
