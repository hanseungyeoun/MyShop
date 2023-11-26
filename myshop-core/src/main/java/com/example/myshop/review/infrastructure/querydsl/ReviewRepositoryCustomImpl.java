package com.example.myshop.review.infrastructure.querydsl;

import com.example.myshop.order.domain.OrderStatus;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.example.myshop.order.domain.QOrder.order;
import static com.example.myshop.order.domain.item.QOrderItem.orderItem;
import static com.example.myshop.order.domain.item.QOrderItemOption.orderItemOption;
import static com.example.myshop.order.domain.item.QOrderItemOptionGroup.orderItemOptionGroup;
import static com.example.myshop.item.domain.item.QItem.item;
import static com.example.myshop.item.domain.item.QItemImage.itemImage;
import static com.example.myshop.review.domain.QReview.review;
import static com.example.myshop.review.domain.QReviewImage.reviewImage;
import static com.example.myshop.review.domain.ReviewInfo.*;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.Projections.list;
import static java.util.stream.Collectors.groupingBy;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public Main findReviewById(Long reviewId) {
        Map<Long, Main> resultMap = queryFactory
                .select(review)
                .from(review)
                .leftJoin(item).on(review.itemId.eq(item.id))
                .leftJoin(item.itemImages, itemImage)
                .leftJoin(review.reviewImages, reviewImage)
                .orderBy(review.createdAt.desc())
                .where(review.id.eq(reviewId))
                .transform(GroupBy.groupBy(review.id).
                        as(constructor(Main.class,
                                review.id,
                                review.memberId,
                                review.itemId,
                                item.itemName,
                                review.orderId,
                                review.reviewText,
                                review.reviewGrade,
                                list(itemImage.imagePath),
                                list(reviewImage.imagePath))));

        Main result = resultMap.getOrDefault(reviewId, null);
        setAdditionalReviewInfo(result);
        return result;
    }

    @Override
    public Page<Main> findAllByItemId(Long itemId, Pageable pageable) {
        List<Main> result = queryFactory
                .select(review)
                .from(review)
                .leftJoin(item).on(review.itemId.eq(item.id))
                .leftJoin(item.itemImages, itemImage)
                .leftJoin(review.reviewImages, reviewImage)
                .orderBy(review.createdAt.desc())
                .where(item.id.eq(itemId))
                .transform(GroupBy.groupBy(review.id).
                        list(constructor(Main.class,
                                review.id,
                                review.memberId,
                                review.itemId,
                                item.itemName,
                                review.orderId,
                                review.reviewText,
                                review.reviewGrade,
                                list(itemImage.imagePath),
                                list(reviewImage.imagePath))));

        setAdditionalReviewInfos(result);
        return PageableExecutionUtils.getPage(result, pageable, this::reviewTotalCount);
    }

    @Override
    public List<WriteAbleReviewInfo> findWriteAbleReviewList(Long memberId) {
        List<WriteAbleReviewInfo> result = queryFactory
                .selectDistinct(constructor(WriteAbleReviewInfo.class,
                        orderItem.itemId,
                        order.memberId,
                        order.id,
                        order.status))
                .from(order)
                .leftJoin(order.orderItems, orderItem)
                .leftJoin(orderItem.orderItemOptionGroups, orderItemOptionGroup)
                .leftJoin(orderItemOptionGroup.orderItemOptions, orderItemOption)
                .where(order.memberId.eq(memberId).and(orderConfirmation())
                        .and(JPAExpressions.selectFrom(review)
                                .where(review.orderId.eq(order.id)).notExists()))
                .fetch();

        setAdditionalWriteAbleInfo(result);
        return result;
    }

    private Long reviewTotalCount() {
        return queryFactory.select(review.count()).from(review).fetchOne();
    }

    private static BooleanExpression orderConfirmation() {
        return order.status.eq(OrderStatus.PURCHASE_CONFIRMATION);
    }

    private void setAdditionalWriteAbleInfo(List<WriteAbleReviewInfo> result) {
        List<Long> orderIds = toOrderIdsForWriteAbleReviewInfo(result);

        if (orderIds.isEmpty()) return;

        List<OrderItemInfo> orderItemInfos = getOrderItemInfos(orderIds);
        Map<Long, List<OrderItemInfo>> otderItemMap = getOrderItemMap(orderItemInfos);
        result.forEach(order -> order.setOrderItems(otderItemMap.get(order.getOrderId())));
    }

    private void setAdditionalReviewInfos(List<Main> results) {
        List<Long> orderIds = toOrderIdsForReviewInfo(results);
        if (orderIds.isEmpty()) return;

        List<OrderItemInfo> orderItemInfos = getOrderItemInfos(orderIds);
        results.forEach(result -> {
            Optional<OrderItemInfo> orderItemInfoOptional = getOrderItemInfo(orderItemInfos, result.getItemId());
            orderItemInfoOptional.ifPresent(orderItemInfo -> result.setOrderItemOptionGroupInfos(orderItemInfo.getOrderItemOptionGroups()));
        });
    }

    private void setAdditionalReviewInfo(Main result) {
        Long orderId = result.getOrderId();
        List<OrderItemInfo> orderItemInfos = findOrderItemInfoAllByOrderId(orderId);
        getOrderItemInfo(orderItemInfos);

        Optional<OrderItemInfo> orderItemInfoOptional = getOrderItemInfo(orderItemInfos, result.getItemId());
        orderItemInfoOptional.ifPresent(orderItemInfo -> result.setOrderItemOptionGroupInfos(orderItemInfo.getOrderItemOptionGroups()));
    }

    private void getOrderItemInfo(List<OrderItemInfo> orderItemInfos) {
        List<OrderItemOptionGroupInfo> orderOptionGroups = findOrderItemOptionGroupInfoAllByItemIdIn(toOrderItemsIds(orderItemInfos));
        List<OrderItemOptionInfo> orderItemOptions = findOrderItemOptionAllByGroupIdIn(toOrderGroupsIds(orderOptionGroups));

        Map<Long, List<OrderItemOptionGroupInfo>> otderItemOptionGroupMap = getItemOptionGroupMap(orderOptionGroups);
        Map<Long, List<OrderItemOptionInfo>> orderItemOptionMap = getOptionMap(orderItemOptions);

        orderOptionGroups.forEach(group -> group.setOrderItemOptions(orderItemOptionMap.get(group.getOptionGroupId())));
        orderItemInfos.forEach(orderItemInfo -> orderItemInfo.setOrderItemOptionGroups(otderItemOptionGroupMap.get(orderItemInfo.getOrderItemId())));
    }

    private List<OrderItemInfo> getOrderItemInfos(List<Long> orderIds) {
        List<OrderItemInfo> orderItemInfos = findOrderItemInfoAllByOrderIdIn(orderIds);
        getOrderItemInfo(orderItemInfos);
        return orderItemInfos;
    }

    private List<OrderItemInfo> findOrderItemInfoAllByOrderIdIn(List<Long> orderId) {
        return queryFactory
                .select(constructor(OrderItemInfo.class,
                        order.id,
                        orderItem.id,
                        orderItem.orderCount,
                        orderItem.itemId,
                        orderItem.itemName,
                        orderItem.totalAmount,
                        orderItem.itemPrice))
                .from(orderItem)
                .join(orderItem.order, order)
                .orderBy(orderItem.id.desc())
                .where(order.id.in(orderId))
                .fetch();
    }

    private List<OrderItemInfo> findOrderItemInfoAllByOrderId(Long orderId) {
        return queryFactory
                .select(constructor(OrderItemInfo.class,
                        order.id,
                        orderItem.id,
                        orderItem.orderCount,
                        orderItem.itemId,
                        orderItem.itemName,
                        orderItem.totalAmount,
                        orderItem.itemPrice))
                .from(orderItem)
                .join(orderItem.order, order)
                .orderBy(orderItem.id.desc())
                .where(order.id.eq(orderId))
                .fetch();
    }

    private List<OrderItemOptionGroupInfo> findOrderItemOptionGroupInfoAllByItemIdIn(List<Long> orderItemId) {
        return queryFactory
                .select(constructor(OrderItemOptionGroupInfo.class,
                        orderItem.id,
                        orderItemOptionGroup.id,
                        orderItemOptionGroup.ordering,
                        orderItemOptionGroup.itemOptionGroupName))
                .from(orderItemOptionGroup)
                .join(orderItemOptionGroup.orderItem, orderItem)
                .orderBy(orderItemOptionGroup.ordering.asc())
                .where(orderItem.id.in(orderItemId)).fetch();
    }

    private List<OrderItemOptionInfo> findOrderItemOptionAllByGroupIdIn(List<Long> orderGroupId) {
        return queryFactory.select(constructor(OrderItemOptionInfo.class,
                        orderItemOptionGroup.id,
                        orderItemOption.id,
                        orderItemOption.ordering,
                        orderItemOption.itemOptionName))
                .from(orderItemOption)
                .join(orderItemOption.orderItemOptionGroup, orderItemOptionGroup)
                .orderBy(orderItemOption.ordering.asc())
                .where(orderItemOptionGroup.id.in(orderGroupId))
                .fetch();
    }

    private List<Long> toOrderIdsForWriteAbleReviewInfo(List<WriteAbleReviewInfo> reviewInfos) {
        return reviewInfos.stream().map(WriteAbleReviewInfo::getOrderId).toList();
    }

    private List<Long> toOrderIdsForReviewInfo(List<Main> reviewInfos) {
        return reviewInfos.stream().map(Main::getOrderId).toList();
    }

    private List<Long> toOrderItemsIds(List<OrderItemInfo> orderItems) {
        return orderItems.stream().map(OrderItemInfo::getOrderItemId).toList();
    }

    private List<Long> toOrderGroupsIds(List<OrderItemOptionGroupInfo> optionGroups) {
        return optionGroups.stream().map(OrderItemOptionGroupInfo::getOptionGroupId).toList();
    }


    private static Optional<OrderItemInfo> getOrderItemInfo(List<OrderItemInfo> orderItemInfos, Long itemId) {
        Optional<OrderItemInfo> orderItemInfoOptional = orderItemInfos.stream()
                .filter(orderItemInfo -> orderItemInfo.getOrderItemId().equals(itemId))
                .findFirst();
        return orderItemInfoOptional;
    }

    private static Map<Long, List<OrderItemInfo>> getOrderItemMap(List<OrderItemInfo> orderItems) {
        return orderItems.stream().collect(groupingBy(OrderItemInfo::getOrderId));
    }

    private static Map<Long, List<OrderItemOptionGroupInfo>> getItemOptionGroupMap(List<OrderItemOptionGroupInfo> itemOptionGroups) {
        return itemOptionGroups.stream().collect(groupingBy(OrderItemOptionGroupInfo::getOrderItemId));
    }

    private static Map<Long, List<OrderItemOptionInfo>> getOptionMap(List<OrderItemOptionInfo> itemOptions) {
        return itemOptions.stream().collect(groupingBy(OrderItemOptionInfo::getOptionGroupId));
    }
}
