package com.example.myshop.item.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItem is a Querydsl query type for Item
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItem extends EntityPathBase<Item> {

    private static final long serialVersionUID = 1603610116L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItem item = new QItem("item");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    public final NumberPath<Long> adminId = createNumber("adminId", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<ItemCategory, QItemCategory> itemCategories = this.<ItemCategory, QItemCategory>createList("itemCategories", ItemCategory.class, QItemCategory.class, PathInits.DIRECT2);

    public final StringPath itemDetails = createString("itemDetails");

    public final ListPath<ItemImage, QItemImage> itemImages = this.<ItemImage, QItemImage>createList("itemImages", ItemImage.class, QItemImage.class, PathInits.DIRECT2);

    public final StringPath itemName = createString("itemName");

    public final ListPath<ItemOptionGroup, QItemOptionGroup> itemOptionGroups = this.<ItemOptionGroup, QItemOptionGroup>createList("itemOptionGroups", ItemOptionGroup.class, QItemOptionGroup.class, PathInits.DIRECT2);

    public final com.example.myshop.common.jpa.QMoney itemPrice;

    public final EnumPath<com.example.myshop.item.infrastructure.item.ItemStatus> status = createEnum("status", com.example.myshop.item.infrastructure.item.ItemStatus.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QItem(String variable) {
        this(Item.class, forVariable(variable), INITS);
    }

    public QItem(Path<? extends Item> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItem(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItem(PathMetadata metadata, PathInits inits) {
        this(Item.class, metadata, inits);
    }

    public QItem(Class<? extends Item> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.itemPrice = inits.isInitialized("itemPrice") ? new com.example.myshop.common.jpa.QMoney(forProperty("itemPrice")) : null;
    }

}

