package com.example.myshop.order.infrastructure.querydsl;


import com.example.myshop.order.domain.OrderInfo;
import com.example.myshop.order.dto.OrderSearchCondition;
import com.example.myshop.enums.OrderSearchType;
import com.example.myshop.order.domain.OrderStatus;
import com.example.myshop.order.domain.item.QOrderItemOption;
import com.example.myshop.order.domain.item.QOrderItemOptionGroup;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static com.example.myshop.order.domain.QOrder.*;
import static com.example.myshop.order.domain.item.QOrderItem.*;
import static java.util.stream.Collectors.groupingBy;
import static org.springframework.util.StringUtils.hasText;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public Page<OrderInfo.Main> findOrderAll(OrderSearchCondition cond, Long memberId, Pageable pageable) {
        List<OrderInfo.Main> result = queryFactory
                .selectDistinct(Projections.constructor(OrderInfo.Main.class,
                        order.id,
                        order.memberId,
                        order.payMethod,
                        order.totalAmount,
                        order.deliveryFragment,
                        order.orderedAt,
                        order.status))
                .from(order)
                .join(order.orderItems, orderItem)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(searchValueEp(cond.getSearchType(), cond.getSearchValue()))
                .where(statusEp(cond.getOrderStatus()))
                .where(startDateTimeGoe(cond.getStartDatetime()))
                .where(endDateTimeLoe(cond.getEndDatetime()))
                .where(order.memberId.eq(memberId))
                .orderBy(order.createdAt.desc())
                .fetch();

        setAdditionalInfo(result);
        return PageableExecutionUtils.getPage(result, pageable, () -> orderTotalCount());
    }

    private BooleanExpression searchValueEp(OrderSearchType searchType, String searchValue) {
        if (searchType == null) {
            return null;
        }

        return switch (searchType) {
            case ITEM_NAME -> itemNameContaining(searchValue);
            case ORDER_ID -> orderIdEq(searchValue);
        };
    }

    private BooleanExpression itemNameContaining(String searchValue) {
        return hasText(searchValue) ? orderItem.itemName.contains(searchValue) : null;
    }

    private BooleanExpression orderIdEq(String searchValue) {
        return hasText(searchValue) ? order.id.stringValue().eq(searchValue) : null;
    }

    private BooleanExpression statusEp(OrderStatus status) {
        return  status != null ? order.status.eq(status) : null;
    }

    private BooleanExpression startDateTimeGoe(LocalDate startDateTime) {
        if(startDateTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(startDateTime, LocalTime.MIN);
        return order.createdAt.goe(LocalDateTime.from(localDateTime));
    }

    private BooleanExpression endDateTimeLoe(LocalDate endDateTime) {
        if(endDateTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(endDateTime, LocalTime.MAX);
        return order.createdAt.loe(LocalDateTime.from(localDateTime));
    }



    private void setAdditionalInfo(List<OrderInfo.Main> result) {
        List<Long> orderIds = toOrderIds(result);

        if (orderIds.size() == 0)
            return;

        List<OrderInfo.OrderItemInfo> orderItemInfos = findOrderItemInfoAllByOrderIdIn(orderIds);
        List<OrderInfo.OrderItemOptionGroupInfo> orderOptionGroups = findOrderItemOptionGroupInfoAllByItemIdIn(toOrderItemsIds(orderItemInfos));
        List<OrderInfo.OrderItemOptionInfo> orderItemOptions = findOrderItemOptionAllByGroupIdIn(toOrderGroupsIds(orderOptionGroups));

        Map<Long, List<OrderInfo.OrderItemInfo>> otderItemMap = getOrderItemMap(orderItemInfos);
        Map<Long, List<OrderInfo.OrderItemOptionGroupInfo>> otderItemOptionGroupMap = getItemOptionGroupMap(orderOptionGroups);
        Map<Long, List<OrderInfo.OrderItemOptionInfo>> orderItemOptionMap = getOptionMap(orderItemOptions);


        orderOptionGroups.forEach(group -> group.setOrderItemOptions(orderItemOptionMap.get(group.getOptionGroupId())));
        orderItemInfos.forEach(item -> item.setOrderItemOptionGroups(otderItemOptionGroupMap.get(item.getOrderItemId())));
        result.forEach(order -> order.setOrderItems(otderItemMap.get(order.getOrderId())));
    }

    private List<Long> toOrderIds(List<OrderInfo.Main> orders) {
        return orders.stream()
                .map(OrderInfo.Main::getOrderId)
                .toList();
    }

    private List<Long> toOrderItemsIds(List<OrderInfo.OrderItemInfo> orderItems) {
        return orderItems.stream()
                .map(OrderInfo.OrderItemInfo::getOrderItemId)
                .toList();
    }

    private List<Long> toOrderGroupsIds(List<OrderInfo.OrderItemOptionGroupInfo> optionGroups) {
        return optionGroups.stream()
                .map(OrderInfo.OrderItemOptionGroupInfo::getOptionGroupId)
                .toList();
    }

    private List<OrderInfo.OrderItemInfo> findOrderItemInfoAllByOrderIdIn(List<Long> orderId) {
        return queryFactory
                .select(Projections.constructor(OrderInfo.OrderItemInfo.class,
                        order.id,
                        orderItem.id,
                        orderItem.orderCount,
                        orderItem.itemId,
                        orderItem.itemName,
                        orderItem.memberId,
                        orderItem.totalAmount,
                        orderItem.itemPrice,
                        orderItem.deliveryStatus))
                .from(orderItem)
                .join(orderItem.order, order)
                .orderBy(orderItem.id.desc())
                .where(order.id.in(orderId))
                .fetch();
    }

    private List<OrderInfo.OrderItemOptionGroupInfo> findOrderItemOptionGroupInfoAllByItemIdIn(List<Long> orderItemId) {
        return queryFactory
                .select(Projections.constructor(OrderInfo.OrderItemOptionGroupInfo.class,
                        orderItem.id,
                        QOrderItemOptionGroup.orderItemOptionGroup.id,
                        QOrderItemOptionGroup.orderItemOptionGroup.ordering,
                        QOrderItemOptionGroup.orderItemOptionGroup.itemOptionGroupName))
                .from(QOrderItemOptionGroup.orderItemOptionGroup)
                .join(QOrderItemOptionGroup.orderItemOptionGroup.orderItem, orderItem)
                .orderBy(QOrderItemOptionGroup.orderItemOptionGroup.ordering.asc())
                .where(orderItem.id.in(orderItemId))
                .fetch();
    }


    private List<OrderInfo.OrderItemOptionInfo> findOrderItemOptionAllByGroupIdIn(List<Long> orderGroupId) {
        return queryFactory
                .select(Projections.constructor(OrderInfo.OrderItemOptionInfo.class,
                        QOrderItemOptionGroup.orderItemOptionGroup.id,
                        QOrderItemOption.orderItemOption.id,
                        QOrderItemOption.orderItemOption.ordering,
                        QOrderItemOption.orderItemOption.itemOptionName
                ))
                .from(QOrderItemOption.orderItemOption)
                .join(QOrderItemOption.orderItemOption.orderItemOptionGroup, QOrderItemOptionGroup.orderItemOptionGroup)
                .orderBy(QOrderItemOption.orderItemOption.ordering.asc())
                .where(QOrderItemOptionGroup.orderItemOptionGroup.id.in(orderGroupId))
                .fetch();
    }

    private static Map<Long, List<OrderInfo.OrderItemInfo>> getOrderItemMap(List<OrderInfo.OrderItemInfo> orderItems) {
        return orderItems.stream()
                .collect(groupingBy(OrderInfo.OrderItemInfo::getOrderId));
    }

    private static Map<Long, List<OrderInfo.OrderItemOptionGroupInfo>> getItemOptionGroupMap(List<OrderInfo.OrderItemOptionGroupInfo> itemOptionGroups) {
        return itemOptionGroups.stream()
                .collect(groupingBy(OrderInfo.OrderItemOptionGroupInfo::getOrderItemId));
    }

    private static Map<Long, List<OrderInfo.OrderItemOptionInfo>> getOptionMap(List<OrderInfo.OrderItemOptionInfo> itemOptions) {
        return itemOptions.stream()
                .collect(groupingBy(OrderInfo.OrderItemOptionInfo::getOptionGroupId));
    }

    private Long orderTotalCount() {
        return queryFactory
                .select(order.count())
                .from(order)
                .fetchOne();
    }
}