package com.example.myshop.item.infrastructure.item.querydsql;

import com.example.myshop.item.dto.ItemSearchCondition;
import com.example.myshop.enums.ItemSearchType;
import com.example.myshop.item.infrastructure.item.ItemStatus;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLTemplates;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import static com.example.myshop.item.domain.item.ItemInfo.*;
import static com.example.myshop.item.domain.category.QCategory.category;
import static com.example.myshop.item.domain.item.QItem.item;
import static com.example.myshop.item.domain.item.QItemImage.itemImage;
import static com.example.myshop.item.domain.item.QItemCategory.itemCategory;
import static com.example.myshop.item.domain.item.QItemOption.itemOption;
import static com.example.myshop.item.domain.item.QItemOptionGroup.itemOptionGroup;
import static java.util.stream.Collectors.*;
import static org.springframework.util.StringUtils.hasText;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(JPQLTemplates.DEFAULT, em);
    }

    @Override
    public Page<Main> findItemAll(ItemSearchCondition cond, Pageable pageable) {
        List<Main> result = queryFactory
                .selectDistinct(Projections.constructor(Main.class,
                        item.id,
                        item.itemName,
                        item.itemPrice,
                        item.itemDetails,
                        item.status))
                .from(item)
                .leftJoin(item.itemCategories, itemCategory)
                .join(category).on(itemCategory.categoryId.eq(category.id))
                .where(searchValueEp(cond.getSearchType(), cond.getSearchValue()))
                .where(statusEp(cond.getItemStatus()))
                .where(startDateTimeGoe(cond.getStartDatetime()))
                .where(endDateTimeLoe(cond.getEndDatetime()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getAllOrderSpecifiers(pageable).stream().toArray(OrderSpecifier[]::new))
                .fetch();

        setAdditionalInfo(result);
        return PageableExecutionUtils.getPage(result, pageable, this::itemTotalCount);
    }

    private BooleanExpression searchValueEp(ItemSearchType searchType, String searchValue) {
        if (searchType == null) {
            return null;
        }

        return switch (searchType) {
            case ITEM_NAME -> itemNameContaining(searchValue);
            case ITEM_ID -> itemIdEq(searchValue);
            case CATEGORY_PATH -> categoryPathContaining(searchValue);
        };
    }

    private BooleanExpression itemNameContaining(String searchValue) {
        return hasText(searchValue) ? item.itemName.contains(searchValue) : null;
    }

    private BooleanExpression itemIdEq(String searchValue) {
        return hasText(searchValue) ? item.id.stringValue().eq(searchValue) : null;
    }

    private BooleanExpression categoryPathContaining(String categoryPath) {
        return hasText(categoryPath) ? category.path.like(categoryPath+"%") : null;
    }

    private BooleanExpression statusEp(ItemStatus itemStatus) {
        return  itemStatus != null ? item.status.eq(itemStatus) : null;
    }

    private BooleanExpression startDateTimeGoe(LocalDate startDateTime) {
        if(startDateTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(startDateTime, LocalTime.MIN);
        return item.createdAt.goe(LocalDateTime.from(localDateTime));
    }

    private BooleanExpression endDateTimeLoe(LocalDate endDateTime) {
        if(endDateTime == null)
            return null;

        LocalDateTime localDateTime = LocalDateTime.of(endDateTime, LocalTime.MAX);
        return item.createdAt.loe(LocalDateTime.from(localDateTime));
    }

    private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "itemName":
                        orders.add(new OrderSpecifier(direction, item.itemName));
                        break;

                    case "id":
                        orders.add(new OrderSpecifier(direction, item.id));
                        break;

                    case "createdDate":
                        orders.add(new OrderSpecifier(direction, item.createdAt));
                        break;

                    case "categoryPath":
                        orders.add(new OrderSpecifier(direction, category.path));
                        break;

                    default:
                        break;
                }
            }
        }
        return orders;
    }

    private void setAdditionalInfo(List<Main> result) {
        List<Long> itemIds = toItemIds(result);

        if (itemIds.size() == 0)
            return;

        List<Tuple> itemImages = findItemImageByItemIdIn(itemIds);
        List<CategoryInfo> categoryInfos = findCategoryInfoByItemIdIn(itemIds);

        List<ItemOptionGroupInfo> itemOptionGroups = findOptionGroupAllByItemIdIn(itemIds);
        List<ItemOptionInfo> itemOptions = findOptionAllByGroupIdIn(toOptionGroupIds(itemOptionGroups));

        Map<Long, List<String>> imageMap = getImageMap(itemImages);
        Map<Long, List<ItemOptionGroupInfo>> itemOptionGroupMap = getItemOptionGroupMap(itemOptionGroups);
        Map<Long, List<ItemOptionInfo>> itemOptionMap = getOptionMap(itemOptions);
        Map<Long, List<CategoryInfo>> categoryInfoMap = getCategoryInfoMap(categoryInfos);

        itemOptionGroups.forEach(group -> group.setItemOptions(itemOptionMap.get(group.getItemOptionGroupId())));
        result.forEach(item -> {
            item.setCategoryInfos(categoryInfoMap.get(item.getItemId()));
            item.setItemOptionGroups(itemOptionGroupMap.get(item.getItemId()));
            item.setItemImages(imageMap.get(item.getItemId()));
        });
    }

    private List<Long> toItemIds(List<Main> items) {
        return items.stream()
                .map(Main::getItemId)
                .toList();
    }

    private List<Long> toOptionGroupIds(List<ItemOptionGroupInfo> groups) {
        return groups.stream()
                .map(ItemOptionGroupInfo::getItemOptionGroupId)
                .toList();
    }

    private List<Tuple> findItemImageByItemIdIn(List<Long> itemId) {
        return queryFactory
                .select(
                        item.id,
                        itemImage.imagePath
                )
                .from(item)
                .join(item.itemImages, itemImage)
                .where(item.id.in(itemId))
                .fetch();
    }

    private List<CategoryInfo> findCategoryInfoByItemIdIn(List<Long> itemId) {
        return queryFactory
                .select(Projections.constructor(CategoryInfo.class,
                        item.id,
                        category.id,
                        category.parentCategoryId,
                        category.path,
                        category.name))
                .from(item)
                .join(item.itemCategories, itemCategory)
                .join(category).on(itemCategory.categoryId.eq(category.id))
                .where(item.id.in(itemId))
                .fetch();
    }

    private List<ItemOptionGroupInfo> findOptionGroupAllByItemIdIn(List<Long> itemId) {
        return queryFactory
                .select(Projections.constructor(ItemOptionGroupInfo.class,
                        item.id,
                        itemOptionGroup.id,
                        itemOptionGroup.ordering,
                        itemOptionGroup.itemOptionGroupName
                ))
                .from(item)
                .join(item.itemOptionGroups, itemOptionGroup)
                .orderBy(itemOptionGroup.ordering.asc())
                .where(item.id.in(itemId))
                .fetch();
    }

    private List<ItemOptionInfo> findOptionAllByGroupIdIn(List<Long> groupId) {
        return queryFactory
                .select(Projections.constructor(ItemOptionInfo.class,
                        itemOptionGroup.id,
                        itemOption.id,
                        itemOption.ordering,
                        itemOption.itemOptionName
                ))
                .from(itemOption)
                .join(itemOption.itemOptionGroup, itemOptionGroup)
                .orderBy(itemOption.ordering.asc())
                .where(itemOptionGroup.id.in(groupId))
                .fetch();
    }

    private static Map<Long, List<String>> getImageMap(List<Tuple> itemImages) {
        return itemImages.stream()
                .collect(groupingBy(tuple -> tuple.get(item.id),
                                mapping(tuple -> tuple.get(itemImage.imagePath), toList())));
    }

    private static Map<Long, List<CategoryInfo>> getCategoryInfoMap(List<CategoryInfo> categoryInfos) {
        return categoryInfos.stream()
                .collect(groupingBy(CategoryInfo::getItemId));
    }

    private static Map<Long, List<ItemOptionGroupInfo>> getItemOptionGroupMap(List<ItemOptionGroupInfo> itemOptionGroups) {
        return itemOptionGroups.stream()
                .collect(groupingBy(ItemOptionGroupInfo::getItemId));
    }

    private static Map<Long, List<ItemOptionInfo>> getOptionMap(List<ItemOptionInfo> itemOptions) {
        return itemOptions.stream()
                .collect(groupingBy(ItemOptionInfo::getItemOptionGroupId));
    }

    private Long itemTotalCount() {
        return queryFactory
                .select(item.count())
                .from(item)
                .fetchOne();
    }
}
