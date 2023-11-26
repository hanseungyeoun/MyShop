package com.example.myshop.order.domain.item;

import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.common.jpa.MoneyConverter;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.discount.DiscountCalculator;
import com.example.myshop.order.dto.OptionGroup;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "order_items")
@Entity
public class OrderItem extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_item_id")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderCount;
    private Long itemId;
    private String itemName;
    private Long memberId;
    @AttributeOverride(name = "money", column = @Column(name = "total_amount"))
    private final Money totalAmount = Money.ZERO;

    @Embedded
    @Convert(attributeName = "item_price", converter = MoneyConverter.class)
    @AttributeOverride(name = "money", column = @Column(name = "item_price"))
    private Money itemPrice = Money.ZERO;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderItem", cascade = CascadeType.ALL)
    @OrderBy("ordering asc")
    private final List<OrderItemOptionGroup> orderItemOptionGroups = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "order_item_coupon",
            joinColumns = @JoinColumn(name = "orders_item_id"))
    private final List<Long> issueCouponIds = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;

    @Builder
    public OrderItem(
            Integer orderCount,
            Long itemId,
            String itemName,
            Long memberId,
            Money itemPrice,
            List<Long> issueCouponIds,
            List<OrderItemOptionGroup> orderItemOptionGroups
    ) {
        if (orderCount == null) throw new InvalidParamException("OrderItemLine.orderCount");
        if (memberId == null) throw new InvalidParamException("OrderItemLine.memberId");
        if (itemId == null && !StringUtils.hasText(itemName))
            throw new InvalidParamException("OrderItemLine.itemNo and itemName");
        if (itemPrice == null) throw new InvalidParamException("OrderItemLine.itemPrice");
        if (orderItemOptionGroups == null) throw new InvalidParamException("OrderItemLine.orderItemOptionGroups");

        this.orderCount = orderCount;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.memberId = memberId;
        this.deliveryStatus = DeliveryStatus.BEFORE_DELIVERY;
        setOptionGroups(orderItemOptionGroups);

        if(issueCouponIds != null) {
            for (Long issueCouponId : issueCouponIds) {
                this.issueCouponIds.add(issueCouponId);
            }
        }
    }

    public void setOptionGroups(List<OrderItemOptionGroup> orderItemOptionGroups) {
        for (OrderItemOptionGroup orderItemOptionGroup : orderItemOptionGroups) {
            setOptionGroup(orderItemOptionGroup);
        }
    }

    public void setOptionGroup(OrderItemOptionGroup orderItemOptionGroup) {
        this.orderItemOptionGroups.add(orderItemOptionGroup);
        orderItemOptionGroup.setOrderItem(this);
    }

    public Money calculateTotalAmount(DiscountCalculator discountCalculator) {
        return discountCalculator.calculateDiscountAmounts(itemPrice.times(orderCount), issueCouponIds, memberId);
    }

    public void deliveryPrepare() {
        if (this.deliveryStatus != DeliveryStatus.BEFORE_DELIVERY) throw new IllegalStatusException();
        this.deliveryStatus = DeliveryStatus.DELIVERY_PREPARE;
    }

    public void inDelivery() {
        if (this.deliveryStatus != DeliveryStatus.DELIVERY_PREPARE) throw new IllegalStatusException();
        this.deliveryStatus = DeliveryStatus.DELIVERING;
    }

    public void deliveryComplete() {
        if (this.deliveryStatus != DeliveryStatus.DELIVERING) throw new IllegalStatusException();
        this.deliveryStatus = DeliveryStatus.COMPLETE_DELIVERY;
    }

    public List<OptionGroup> convertToOptionGroup() {
        return orderItemOptionGroups.stream().map(OrderItemOptionGroup::convertToOptionGroup).toList();
    }

    public List<Long> getIssueCoupons() {
        return this.issueCouponIds;
    }
}