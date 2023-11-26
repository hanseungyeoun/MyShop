package com.example.myshop.item.domain.item;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QItemOptionGroup is a Querydsl query type for ItemOptionGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QItemOptionGroup extends EntityPathBase<ItemOptionGroup> {

    private static final long serialVersionUID = 1263982182L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QItemOptionGroup itemOptionGroup = new QItemOptionGroup("itemOptionGroup");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QItem item;

    public final StringPath itemOptionGroupName = createString("itemOptionGroupName");

    public final ListPath<ItemOption, QItemOption> itemOptions = this.<ItemOption, QItemOption>createList("itemOptions", ItemOption.class, QItemOption.class, PathInits.DIRECT2);

    public final NumberPath<Integer> ordering = createNumber("ordering", Integer.class);

    public QItemOptionGroup(String variable) {
        this(ItemOptionGroup.class, forVariable(variable), INITS);
    }

    public QItemOptionGroup(Path<? extends ItemOptionGroup> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QItemOptionGroup(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QItemOptionGroup(PathMetadata metadata, PathInits inits) {
        this(ItemOptionGroup.class, metadata, inits);
    }

    public QItemOptionGroup(Class<? extends ItemOptionGroup> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.item = inits.isInitialized("item") ? new QItem(forProperty("item"), inits.get("item")) : null;
    }

}

