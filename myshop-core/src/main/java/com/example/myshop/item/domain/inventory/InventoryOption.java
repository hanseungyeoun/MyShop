package com.example.myshop.item.domain.inventory;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "inventory_options")
@Entity
public class InventoryOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_option_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "inventory_option_group_id")
    private InventoryOptionGroup inventoryOptionGroup;

    private Integer ordering;
    private String itemOptionName;

    @Builder
    public InventoryOption(
            Integer ordering,
            String itemOptionName
    ) {
        if (ordering == null) throw new InvalidParamException();
        if (!StringUtils.hasText(itemOptionName)) throw new InvalidParamException();

        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
    }

}