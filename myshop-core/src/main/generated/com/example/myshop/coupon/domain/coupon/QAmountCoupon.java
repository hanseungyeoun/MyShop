package com.example.myshop.coupon.domain.coupon;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAmountCoupon is a Querydsl query type for AmountCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAmountCoupon extends EntityPathBase<AmountCoupon> {

    private static final long serialVersionUID = 1683466921L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAmountCoupon amountCoupon = new QAmountCoupon("amountCoupon");

    public final QCoupon _super = new QCoupon(this);

    //inherited
    public final NumberPath<Long> adminId = _super.adminId;

    //inherited
    public final ListPath<CouponItem, QCouponItem> couponItems = _super.couponItems;

    //inherited
    public final StringPath couponName = _super.couponName;

    public final com.example.myshop.common.jpa.QMoney discountAmount;

    //inherited
    public final DatePath<java.time.LocalDate> expirationDate = _super.expirationDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public QAmountCoupon(String variable) {
        this(AmountCoupon.class, forVariable(variable), INITS);
    }

    public QAmountCoupon(Path<? extends AmountCoupon> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAmountCoupon(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAmountCoupon(PathMetadata metadata, PathInits inits) {
        this(AmountCoupon.class, metadata, inits);
    }

    public QAmountCoupon(Class<? extends AmountCoupon> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.discountAmount = inits.isInitialized("discountAmount") ? new com.example.myshop.common.jpa.QMoney(forProperty("discountAmount")) : null;
    }

}

