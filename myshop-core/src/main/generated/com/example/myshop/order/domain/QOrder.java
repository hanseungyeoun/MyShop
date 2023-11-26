package com.example.myshop.order.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -882000321L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final com.example.myshop.order.domain.fragment.QDeliveryFragment deliveryFragment;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> orderedAt = createDateTime("orderedAt", java.time.LocalDateTime.class);

    public final ListPath<com.example.myshop.order.domain.item.OrderItem, com.example.myshop.order.domain.item.QOrderItem> orderItems = this.<com.example.myshop.order.domain.item.OrderItem, com.example.myshop.order.domain.item.QOrderItem>createList("orderItems", com.example.myshop.order.domain.item.OrderItem.class, com.example.myshop.order.domain.item.QOrderItem.class, PathInits.DIRECT2);

    public final StringPath payMethod = createString("payMethod");

    public final EnumPath<OrderStatus> status = createEnum("status", OrderStatus.class);

    public final com.example.myshop.common.jpa.QMoney totalAmount;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.deliveryFragment = inits.isInitialized("deliveryFragment") ? new com.example.myshop.order.domain.fragment.QDeliveryFragment(forProperty("deliveryFragment")) : null;
        this.totalAmount = inits.isInitialized("totalAmount") ? new com.example.myshop.common.jpa.QMoney(forProperty("totalAmount")) : null;
    }

}

