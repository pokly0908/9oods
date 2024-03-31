package com.kuku9.goods.domain.product_order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductOrder is a Querydsl query type for ProductOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductOrder extends EntityPathBase<ProductOrder> {

    private static final long serialVersionUID = -1117333538L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductOrder productOrder = new QProductOrder("productOrder");

    public final StringPath address = createString("address");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public final com.kuku9.goods.domain.user.entity.QUser user;

    public QProductOrder(String variable) {
        this(ProductOrder.class, forVariable(variable), INITS);
    }

    public QProductOrder(Path<? extends ProductOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductOrder(PathMetadata metadata, PathInits inits) {
        this(ProductOrder.class, metadata, inits);
    }

    public QProductOrder(Class<? extends ProductOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.kuku9.goods.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

