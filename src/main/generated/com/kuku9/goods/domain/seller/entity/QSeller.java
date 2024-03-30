package com.kuku9.goods.domain.seller.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = -1744127113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeller seller = new QSeller("seller");

    public final com.kuku9.goods.global.common.entity.QBaseEntity _super = new com.kuku9.goods.global.common.entity.QBaseEntity(this);

    public final StringPath brandName = createString("brandName");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    public final StringPath domainNmae = createString("domainNmae");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath phoneNumber = createString("phoneNumber");

    public final ListPath<com.kuku9.goods.domain.product.entity.Product, com.kuku9.goods.domain.product.entity.QProduct> products = this.<com.kuku9.goods.domain.product.entity.Product, com.kuku9.goods.domain.product.entity.QProduct>createList("products", com.kuku9.goods.domain.product.entity.Product.class, com.kuku9.goods.domain.product.entity.QProduct.class, PathInits.DIRECT2);

    public final com.kuku9.goods.domain.user.entity.QUser user;

    public QSeller(String variable) {
        this(Seller.class, forVariable(variable), INITS);
    }

    public QSeller(Path<? extends Seller> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeller(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeller(PathMetadata metadata, PathInits inits) {
        this(Seller.class, metadata, inits);
    }

    public QSeller(Class<? extends Seller> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.kuku9.goods.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

