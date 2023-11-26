package com.example.myshop.coupon.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCouponItem is a Querydsl query type for CouponItem
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCouponItem extends EntityPathBase<CouponItem> {

    private static final long serialVersionUID = 1066599204L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCouponItem couponItem = new QCouponItem("couponItem");

    public final QCoupon coupon;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> itemId = createNumber("itemId", Long.class);

    public QCouponItem(String variable) {
        this(CouponItem.class, forVariable(variable), INITS);
    }

    public QCouponItem(Path<? extends CouponItem> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCouponItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCouponItem(PathMetadata metadata, PathInits inits) {
        this(CouponItem.class, metadata, inits);
    }

    public QCouponItem(Class<? extends CouponItem> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.coupon = inits.isInitialized("coupon") ? new QCoupon(forProperty("coupon")) : null;
    }

}

