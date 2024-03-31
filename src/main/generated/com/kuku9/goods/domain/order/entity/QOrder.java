package com.kuku9.goods.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -253251937L;

    public static final QOrder order = new QOrder("order1");

    public final ListPath<com.kuku9.goods.domain.cart.entity.Cart, com.kuku9.goods.domain.cart.entity.QCart> carts = this.<com.kuku9.goods.domain.cart.entity.Cart, com.kuku9.goods.domain.cart.entity.QCart>createList("carts", com.kuku9.goods.domain.cart.entity.Cart.class, com.kuku9.goods.domain.cart.entity.QCart.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath status = createString("status");

    public QOrder(String variable) {
        super(Order.class, forVariable(variable));
    }

    public QOrder(Path<? extends Order> path) {
        super(path.getType(), path.getMetadata());
    }

    public QOrder(PathMetadata metadata) {
        super(Order.class, metadata);
    }

}

