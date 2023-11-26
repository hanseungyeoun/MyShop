package com.example.myshop.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QOrderStatistics is a Querydsl query type for OrderStatistics
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderStatistics extends EntityPathBase<OrderStatistics> {

    private static final long serialVersionUID = -852803998L;

    public static final QOrderStatistics orderStatistics = new QOrderStatistics("orderStatistics");

    public final NumberPath<Integer> amount = createNumber("amount", Integer.class);

    public final NumberPath<Integer> count = createNumber("count", Integer.class);

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QOrderStatistics(String variable) {
        super(OrderStatistics.class, forVariable(variable));
    }

    public QOrderStatistics(Path<? extends OrderStatistics> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrderStatistics(PathMetadata metadata) {
        super(OrderStatistics.class, metadata);
    }

}

