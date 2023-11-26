package com.example.myshop.coupon.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRateCoupon is a Querydsl query type for RateCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRateCoupon extends EntityPathBase<RateCoupon> {

    private static final long serialVersionUID = 2070521841L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRateCoupon rateCoupon = new QRateCoupon("rateCoupon");

    public final QCoupon _super = new QCoupon(this);

    //inherited
    public final NumberPath<Long> adminId = _super.adminId;

    //inherited
    public final ListPath<CouponItem, QCouponItem> couponItems = _super.couponItems;

    //inherited
    public final StringPath couponName = _super.couponName;

    public final com.example.myshop.common.jpa.QRatio discountRate;

    //inherited
    public final DatePath<java.time.LocalDate> expirationDate = _super.expirationDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public QRateCoupon(String variable) {
        this(RateCoupon.class, forVariable(variable), INITS);
    }

    public QRateCoupon(Path<? extends RateCoupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRateCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRateCoupon(PathMetadata metadata, PathInits inits) {
        this(RateCoupon.class, metadata, inits);
    }

    public QRateCoupon(Class<? extends RateCoupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.discountRate = inits.isInitialized("discountRate") ? new com.example.myshop.common.jpa.QRatio(forProperty("discountRate")) : null;
    }

}

