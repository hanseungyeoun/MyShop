package com.example.myshop.order.domain;


import com.example.myshop.common.jpa.AbstractEntity;
import com.example.myshop.common.jpa.Money;
import com.example.myshop.exception.IllegalStatusException;
import com.example.myshop.exception.InvalidParamException;
import com.example.myshop.order.domain.discount.DiscountCalculator;
import com.example.myshop.order.domain.fragment.DeliveryFragment;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.dto.OptionGroup;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private Long memberId;
    private String payMethod;

    @AttributeOverride(name = "money", column = @Column(name = "total_amount"))
    private final Money totalAmount = Money.ZERO;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.ALL)
    private final List<OrderItem> orderItems = new ArrayList<>();

    @Embedded
    private DeliveryFragment deliveryFragment;

    private LocalDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //==생성 메서드==//
    @Builder
    public Order(
            Long memberId,
            String payMethod,
            DeliveryFragment deliveryFragment,
            List<OrderItem> orderItems
    ) {
        if (memberId == null) throw new InvalidParamException("Order.userId");
        if (StringUtils.isEmpty(payMethod)) throw new InvalidParamException("Order.payMethod");
        if (deliveryFragment == null) throw new InvalidParamException("Order.deliveryFragment");
        if (orderItems == null) throw new InvalidParamException("Order.orderItems");

        this.memberId = memberId;
        this.payMethod = payMethod;
        this.deliveryFragment = deliveryFragment;
        this.orderedAt = LocalDateTime.now();
        this.status = OrderStatus.INIT;

        addOrderItems(orderItems);
    }

    public void addOrderItems(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            addOrderItem(orderItem);
        }
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    //==비즈니스 로직==//
    public void orderComplete() {
        if (this.status != OrderStatus.INIT) throw new IllegalStatusException();
        this.status = OrderStatus.ORDER_COMPLETE;
    }
    public void orderCancel() {
        verifyChangeDeliveryState();
        this.status = OrderStatus.CANCELED;
    }

    // TODO - 개별 배송도 구현
    public void deliveryPrepare() {
        if (this.status != OrderStatus.ORDER_COMPLETE) throw new IllegalStatusException();
        this.status = OrderStatus.DELIVERY_PREPARE;
        this.orderItems.forEach(OrderItem::deliveryPrepare);
    }

    // TODO - 개별 배송도 구현
    public void inDelivery() {
        if (this.status != OrderStatus.DELIVERY_PREPARE) throw new IllegalStatusException();
        this.status = OrderStatus.IN_DELIVERY;
        this.orderItems.forEach(OrderItem::inDelivery);
    }

    // TODO - 개별 배송도 구현
    public void deliveryComplete() {
        if (this.status != OrderStatus.IN_DELIVERY) throw new IllegalStatusException();
        this.status = OrderStatus.DELIVERY_COMPLETE;
        this.orderItems.forEach(OrderItem::deliveryComplete);
    }

    // TODO - 개별 구매 확정도 구현
    public void purchaseConfirmation() {
        if (this.status != OrderStatus.DELIVERY_COMPLETE) throw new IllegalStatusException();
        this.status = OrderStatus.PURCHASE_CONFIRMATION;
    }

    public void updateDeliveryFragment(
            String receiverName,
            String receiverPhone,
            String receiverZipcode,
            String receiverAddress1,
            String receiverAddress2,
            String etcMessage
    ) {

        verifyChangeDeliveryState();
        this.deliveryFragment = DeliveryFragment.builder()
                .receiverName(receiverName)
                .receiverPhone(receiverPhone)
                .receiverZipcode(receiverZipcode)
                .receiverAddress1(receiverAddress1)
                .receiverAddress2(receiverAddress2)
                .etcMessage(etcMessage)
                .build();
    }
    //== Validation 로직==//

    public void validation(OrderValidator orderValidator) {
        orderValidator.validate(this);
    }

    private void verifyChangeDeliveryState() {
        verifyNotCanceled();
        verifyNotYetDelivered();
    }
    //==조회 로직==//

    public boolean isAlreadyPaymentComplete() {
        switch (this.status) {
            case ORDER_COMPLETE:
            case DELIVERY_PREPARE:
            case IN_DELIVERY:
            case DELIVERY_COMPLETE:
            case PURCHASE_CONFIRMATION:
                return true;
        }
        return false;
    }

    public boolean isAlreadyOrderConfirmation() {
        return this.status == OrderStatus.PURCHASE_CONFIRMATION;
    }

    /**
     * 주문 가격 = 주문 상품의 총 가격
     * 주문 하나의 상품의 가격 = (상품 가격 + 상품 옵션 가격) * 주문 갯수 - 할인 금액
     */
    public Money calculateTotalAmount(DiscountCalculator discountCalculator) {
        return Money.sum(orderItems, orderItem -> orderItem.calculateTotalAmount(discountCalculator));
    }

    public Integer calculateTotalCount() {
        return orderItems.stream().mapToInt(value -> value.getOrderCount()).sum();
    }

    public Map<Long, List<OptionGroup>> convertToOptionGroupMap() {
        return orderItems.stream().collect(Collectors.toMap(
                OrderItem::getItemId, OrderItem::convertToOptionGroup));
    }

    public Map<Long, Integer> getOrdeItemrCountMap() {
        return orderItems.stream().collect(Collectors.toMap(
                OrderItem::getItemId, OrderItem::getOrderCount));
    }

    public List<Long> getIssueCoupons() {
        return this.orderItems.stream()
                .map(OrderItem::getIssueCoupons)
                .flatMap(Collection::stream)
                .toList();
    }

    public List<Long> getItemIds() {
        return orderItems.stream().map(OrderItem::getItemId).toList();
    }

    private void verifyNotYetDelivered() {
        if (!isNotYetDelivered())
            throw new IllegalStatusException("배송이 시작 되었습니다.");
    }

    private boolean isNotYetDelivered() {
        return status == OrderStatus.ORDER_COMPLETE || status == OrderStatus.DELIVERY_PREPARE;
    }
    public void verifyCanWriteReview(){
        if(!isAlreadyOrderConfirmation())
            throw new IllegalStatusException();
    }

    private void verifyNotCanceled() {
        if (this.status == OrderStatus.CANCELED) {
            throw new IllegalStatusException("배송이 취소 되었습니다.");
        }
    }


}

