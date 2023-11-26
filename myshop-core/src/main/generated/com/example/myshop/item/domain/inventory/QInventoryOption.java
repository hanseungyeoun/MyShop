package com.example.myshop.item.domain.inventory;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInventoryOption is a Querydsl query type for InventoryOption
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInventoryOption extends EntityPathBase<InventoryOption> {

    private static final long serialVersionUID = -1580553963L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInventoryOption inventoryOption = new QInventoryOption("inventoryOption");

    public final com.example.myshop.common.jpa.QAbstractEntity _super = new com.example.myshop.common.jpa.QAbstractEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final StringPath createdBy = _super.createdBy;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QInventoryOptionGroup inventoryOptionGroup;

    public final StringPath itemOptionName = createString("itemOptionName");

    public final NumberPath<Integer> ordering = createNumber("ordering", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    //inherited
    public final StringPath updatedBy = _super.updatedBy;

    public QInventoryOption(String variable) {
        this(InventoryOption.class, forVariable(variable), INITS);
    }

    public QInventoryOption(Path<? extends InventoryOption> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInventoryOption(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInventoryOption(PathMetadata metadata, PathInits inits) {
        this(InventoryOption.class, metadata, inits);
    }

    public QInventoryOption(Class<? extends InventoryOption> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.inventoryOptionGroup = inits.isInitialized("inventoryOptionGroup") ? new QInventoryOptionGroup(forProperty("inventoryOptionGroup"), inits.get("inventoryOptionGroup")) : null;
    }

}

