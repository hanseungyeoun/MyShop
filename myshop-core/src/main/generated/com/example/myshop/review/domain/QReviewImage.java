package com.example.myshop.review.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReviewImage is a Querydsl query type for ReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReviewImage extends EntityPathBase<ReviewImage> {

    private static final long serialVersionUID = 489559668L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReviewImage reviewImage = new QReviewImage("reviewImage");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imagePath = createString("imagePath");

    public final QReview review;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QReviewImage(String variable) {
        this(ReviewImage.class, forVariable(variable), INITS);
    }

    public QReviewImage(Path<? extends ReviewImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReviewImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReviewImage(PathMetadata metadata, PathInits inits) {
        this(ReviewImage.class, metadata, inits);
    }

    public QReviewImage(Class<? extends ReviewImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.review = inits.isInitialized("review") ? new QReview(forProperty("review")) : null;
    }

}

