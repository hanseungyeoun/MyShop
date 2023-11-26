package com.example.myshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderEventHistory is a Querydsl query type for OrderEventHistory
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderEventHistory extends EntityPathBase<OrderEventHistory> {

    private static final long serialVersionUID = -960298471L;

    public static final QOrderEventHistory orderEventHistory = new QOrderEventHistory("orderEventHistory");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final StringPath txId = createString("txId");

    public final EnumPath<OrderEventType> type = createEnum("type", OrderEventType.class);

    public QOrderEventHistory(String variable) {
        super(OrderEventHistory.class, forVariable(variable));
    }

    public QOrderEventHistory(Path<? extends OrderEventHistory> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderEventHistory(PathMetadata metadata) {
        super(OrderEventHistory.class, metadata);
    }

}

