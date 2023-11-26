package com.example.myshop.order.infrastructure;

import com.example.myshop.coupon.domain.Issued.IssuedCoupon;
import com.example.myshop.coupon.domain.Issued.IssuedCouponReader;
import com.example.myshop.order.domain.Order;
import com.example.myshop.order.domain.OrderValidator;
import com.example.myshop.order.domain.item.OrderItem;
import com.example.myshop.order.domain.item.OrderItemOptionGroup;
import com.example.myshop.item.domain.inventory.Inventory;
import com.example.myshop.item.domain.inventory.InventoryReader;
import com.example.myshop.item.domain.item.Item;
import com.example.myshop.item.domain.item.ItemReader;
import com.example.myshop.item.domain.item.ItemOptionGroup;
import com.example.myshop.exception.InvalidParamException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
@RequiredArgsConstructor
public class OrderValidatorImpl implements OrderValidator {

    private final ItemReader itemReader;
    private final InventoryReader inventoryReader;
    private final IssuedCouponReader issuedCouponReader;

    @Override
    public void validate(Order order) {
        validate(order, getItems(order), getInventory(order), getCoupons(order));
    }

    public void validate(Order order, Map<Long, Item> items, Map<Long, Inventory> inventory, List<IssuedCoupon> coupons) {
        for (IssuedCoupon coupon : coupons) {
            coupon.verifyCouponAvailable();
        }

        if (order.getOrderItems().isEmpty()) {
            throw new InvalidParamException("주문 항목이 비어 있습니다.");
        }

        for (OrderItem item : order.getOrderItems()) {
            validateOrderItem(item, items.get(item.getItemId()));
        }

        for (OrderItem item : order.getOrderItems()) {
            validateInventoryItem(item, inventory.get(item.getItemId()));
        }
    }

    private void validateInventoryItem(OrderItem item, Inventory stock) {
        if (item.getOrderCount() > stock.getQuantity()) {
            throw new InvalidParamException("재고가 부족 합니다.");
        }
    }

    private void validateOrderItem(OrderItem orderItem, Item item) {
        if (!item.isOnSale()) {
            throw new InvalidParamException("판매 중인 상품이 아닙니다.");
        }

        if (!item.getItemName().equals(orderItem.getItemName())) {
            throw new InvalidParamException("기본 상품명이 변경 됐습니다.");
        }

        if (!item.getItemPrice().equals(orderItem.getItemPrice())) {
            throw new InvalidParamException("기본 상품 가격이 변경됐습니다.");
        }

        for (OrderItemOptionGroup group : orderItem.getOrderItemOptionGroups()) {
            validateOrderOptionGroup(group, item);
        }
    }

    private void validateOrderOptionGroup(OrderItemOptionGroup group, Item item) {
        for (ItemOptionGroup spec : item.getItemOptionGroups()) {
            if (spec.isSatisfiedBy(group.convertToOptionGroup())) {
                return;
            }
        }

        throw new InvalidParamException("옵션이 변경됐습니다.");
    }


    private Map<Long, Item> getItems(Order order) {
        return itemReader.findAllById(order.getItemIds()).stream().collect(toMap(Item::getId, identity()));
    }

    private Map<Long, Inventory> getInventory(Order order) {
        return order.getOrderItems().stream()
                .map(this::getAllByItemIdAndOptionGroupIn)
                .collect(toMap(Inventory::getItemId, identity()));
    }

    private List<IssuedCoupon> getCoupons(Order order) {
        List<Long> issueCoupons = order.getIssueCoupons();
        return issuedCouponReader.findByIdIn(issueCoupons);
    }

    private Inventory getAllByItemIdAndOptionGroupIn(OrderItem orderItem) {
        return inventoryReader.getAllByItemIdAndOptionGroupIn(orderItem.getItemId(),
                orderItem.getOrderItemOptionGroups().stream().map(OrderItemOptionGroup::convertToOptionGroup).toList());
    }
}
