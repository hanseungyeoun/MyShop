package com.example.myshop.common.jpa;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRatio is a Querydsl query type for Ratio
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QRatio extends BeanPath<Ratio> {

    private static final long serialVersionUID = 1328745648L;

    public static final QRatio ratio = new QRatio("ratio");

    public final NumberPath<Double> rate = createNumber("rate", Double.class);

    public QRatio(String variable) {
        super(Ratio.class, forVariable(variable));
    }

    public QRatio(Path<? extends Ratio> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRatio(PathMetadata metadata) {
        super(Ratio.class, metadata);
    }

}

