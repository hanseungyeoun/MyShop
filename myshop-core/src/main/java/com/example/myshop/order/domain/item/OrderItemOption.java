package com.example.myshop.order.domain.item;

import com.example.myshop.order.dto.Option;
import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item_options")
@Entity
public class OrderItemOption extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_option_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_item_option_group_id")
    private OrderItemOptionGroup orderItemOptionGroup;


    private Integer ordering;
    private String itemOptionName;

    @Builder
    public OrderItemOption(
            Integer ordering,
            String itemOptionName
    ) {
        if (ordering == null) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemOptionName)) throw new InvalidParamException();

        this.ordering = ordering;
        this.itemOptionName = itemOptionName;
    }

    public Option convertToOption() {
        return new Option(ordering, itemOptionName);
    }
}