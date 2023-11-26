package com.example.myshop.order.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderItem is a Querydsl query type for OrderItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderItem extends EntityPathBase<OrderItem> {

    private static final long serialVersionUID = 1479724629L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderItem orderItem = new QOrderItem("orderItem");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final EnumPath<DeliveryStatus> deliveryStatus = createEnum("deliveryStatus", DeliveryStatus.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<Long, NumberPath<Long>> issueCouponIds = this.<Long, NumberPath<Long>>createList("issueCouponIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public final StringPath itemName = createString("itemName");

    public final com.example.myshop.common.jpa.QMoney itemPrice;

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final com.example.myshop.order.domain.QOrder order;

    public final NumberPath<Integer> orderCount = createNumber("orderCount", Integer.class);

    public final ListPath<OrderItemOptionGroup, QOrderItemOptionGroup> orderItemOptionGroups = this.<OrderItemOptionGroup, QOrderItemOptionGroup>createList("orderItemOptionGroups", OrderItemOptionGroup.class, QOrderItemOptionGroup.class, PathInits.DIRECT2);

    public final com.example.myshop.common.jpa.QMoney totalAmount;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QOrderItem(String variable) {
        this(OrderItem.class, forVariable(variable), INITS);
    }

    public QOrderItem(Path<? extends OrderItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderItem(PathMetadata metadata, PathInits inits) {
        this(OrderItem.class, metadata, inits);
    }

    public QOrderItem(Class<? extends OrderItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.itemPrice = inits.isInitialized("itemPrice") ? new com.example.myshop.common.jpa.QMoney(forProperty("itemPrice")) : null;
        this.order = inits.isInitialized("order") ? new com.example.myshop.order.domain.QOrder(forProperty("order"), inits.get("order")) : null;
        this.totalAmount = inits.isInitialized("totalAmount") ? new com.example.myshop.common.jpa.QMoney(forProperty("totalAmount")) : null;
    }

}

