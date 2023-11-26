package com.example.myshop.item.domain.inventory;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory_option_groups")
@Entity
public class InventoryOptionGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_option_group_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventoryOptionGroup", cascade = CascadeType.ALL)
    @OrderBy("ordering asc")
    private final List<InventoryOption> inventoryItemOptions = new ArrayList<>();

    //==생성 메서드==//
    @Builder
    public InventoryOptionGroup(Integer ordering, String itemOptionGroupName, List<InventoryOption> inventoryOptions) {
        if (ordering == null) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemOptionGroupName)) throw new InvalidParamException();
        if (inventoryOptions == null) throw new InvalidParamException();

        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;
        this.inventoryItemOptions.addAll(inventoryOptions);

        addOptionGroups(inventoryOptions);
    }

    private void addOptionGroups(List<InventoryOption> inventoryOptions) {
        for (InventoryOption inventoryOption : inventoryOptions) {
            addOptionGroup(inventoryOption);
        }
    }

    private void addOptionGroup(InventoryOption inventoryOption) {
        this.inventoryItemOptions.add(inventoryOption);
        inventoryOption.setInventoryOptionGroup(this);
    }

}