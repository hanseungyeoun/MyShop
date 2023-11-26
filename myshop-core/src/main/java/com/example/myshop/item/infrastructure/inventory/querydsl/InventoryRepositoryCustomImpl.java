package com.example.myshop.item.infrastructure.inventory.querydsl;

import com.example.myshop.order.dto.Option;
import com.example.myshop.order.dto.OptionGroup;
import com.example.myshop.item.domain.inventory.Inventory;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.example.myshop.item.domain.inventory.QInventory.inventory;
import static com.example.myshop.item.domain.inventory.QInventoryOption.inventoryOption;
import static com.example.myshop.item.domain.inventory.QInventoryOptionGroup.inventoryOptionGroup;

public class InventoryRepositoryCustomImpl implements InventoryRepositoryCustom {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public InventoryRepositoryCustomImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Inventory> findAllByItemIdAndOptionGroupIn(Long itemId, List<OptionGroup> optionGroup) {
        Inventory result = queryFactory.selectDistinct(inventory)
                .from(inventory)
                .leftJoin(inventory.inventoryItemOptionGroups, inventoryOptionGroup)
                .leftJoin(inventoryOptionGroup.inventoryItemOptions, inventoryOption)
                .where(itemIdEq(itemId), optionEq(optionGroup))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    private BooleanExpression itemIdEq(Long itemId) {
        return itemId != null ? inventory.itemId.eq(itemId) : null;
    }

    private BooleanBuilder optionEq(List<OptionGroup> optionGroups) {
        BooleanBuilder builder = new BooleanBuilder();

        for (OptionGroup optionGroup : optionGroups) {
            builder.or(optionGroupEq(optionGroup));
        }

        return builder;
    }

    private BooleanBuilder optionGroupEq(OptionGroup optionGroup) {
        List<BooleanExpression> expressions = getOptions(optionGroup.getOptions());
        BooleanBuilder builder = new BooleanBuilder();
        BooleanExpression optionGroupExpression = inventoryOptionGroup.itemOptionGroupName.eq(optionGroup.getItemOptionGroupName());

        builder.and(optionGroupExpression);
        for (BooleanExpression expression : expressions) {
            builder.and(expression);
        }

        return builder;
    }

    private List<BooleanExpression> getOptions(List<Option> options) {
        List<BooleanExpression> list = new ArrayList<>();

        for (Option option : options) {
            list.add(inventoryOption.itemOptionName.eq(option.getItemOptionName()));
        }

        return list;
    }
}
