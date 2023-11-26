package com.example.myshop.item.domain.inventory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryOptionGroup is a Querydsl query type for InventoryOptionGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryOptionGroup extends EntityPathBase<InventoryOptionGroup> {

    private static final long serialVersionUID = 2003485290L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryOptionGroup inventoryOptionGroup = new QInventoryOptionGroup("inventoryOptionGroup");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInventory inventory;

    public final ListPath<InventoryOption, QInventoryOption> inventoryItemOptions = this.<InventoryOption, QInventoryOption>createList("inventoryItemOptions", InventoryOption.class, QInventoryOption.class, PathInits.DIRECT2);

    public final StringPath itemOptionGroupName = createString("itemOptionGroupName");

    public final NumberPath<Integer> ordering = createNumber("ordering", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QInventoryOptionGroup(String variable) {
        this(InventoryOptionGroup.class, forVariable(variable), INITS);
    }

    public QInventoryOptionGroup(Path<? extends InventoryOptionGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryOptionGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryOptionGroup(PathMetadata metadata, PathInits inits) {
        this(InventoryOptionGroup.class, metadata, inits);
    }

    public QInventoryOptionGroup(Class<? extends InventoryOptionGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventory = inits.isInitialized("inventory") ? new QInventory(forProperty("inventory")) : null;
    }

}

