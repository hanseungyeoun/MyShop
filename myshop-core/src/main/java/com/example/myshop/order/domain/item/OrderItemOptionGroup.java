package com.example.myshop.order.domain.item;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.order.dto.OptionGroup;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_item_option_groups")
@Entity
public class OrderItemOptionGroup extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_option_group_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orders_item_id")
    private OrderItem orderItem;

    private Integer ordering;
    private String itemOptionGroupName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItemOptionGroup", cascade = CascadeType.ALL)
    @OrderBy("ordering asc")
    private final List<OrderItemOption> orderItemOptions = new ArrayList<>();

    @Builder
    public OrderItemOptionGroup(
            Integer ordering,
            String itemOptionGroupName,
            List<OrderItemOption> orderItemOptions
    ) {
        if (ordering == null) throw new InvalidParamException();
        if (StringUtils.isEmpty(itemOptionGroupName)) throw new InvalidParamException();
        if (orderItemOptions == null) throw new InvalidParamException();

        this.ordering = ordering;
        this.itemOptionGroupName = itemOptionGroupName;

        addOptionGroups(orderItemOptions);
    }

    public void addOptionGroups(List<OrderItemOption> orderItemOptions) {
        for (OrderItemOption orderItemOption : orderItemOptions) {
            addOptionGroups(orderItemOption);
        }
    }

    public void addOptionGroups(OrderItemOption orderItemOption) {
        this.orderItemOptions.add(orderItemOption);
        orderItemOption.setOrderItemOptionGroup(this);
    }

    public OptionGroup convertToOptionGroup() {
        return new OptionGroup(
                ordering,
                itemOptionGroupName,
                orderItemOptions.stream().map(OrderItemOption::convertToOption).toList()
        );
    }
}
