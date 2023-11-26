package com.example.myshop.coupon.domain.Issued;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIssuedCoupon is a Querydsl query type for IssuedCoupon
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssuedCoupon extends EntityPathBase<IssuedCoupon> {

    private static final long serialVersionUID = -1688489929L;

    public static final QIssuedCoupon issuedCoupon = new QIssuedCoupon("issuedCoupon");

    public final NumberPath<Long> couponId = createNumber("couponId", Long.class);

    public final DatePath<java.time.LocalDate> expirationDate = createDate("expirationDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Long> memberId = createNumber("memberId", Long.class);

    public final NumberPath<Long> orderId = createNumber("orderId", Long.class);

    public final EnumPath<IssuedCouponStatus> status = createEnum("status", IssuedCouponStatus.class);

    public QIssuedCoupon(String variable) {
        super(IssuedCoupon.class, forVariable(variable));
    }

    public QIssuedCoupon(Path<? extends IssuedCoupon> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIssuedCoupon(PathMetadata metadata) {
        super(IssuedCoupon.class, metadata);
    }

}

