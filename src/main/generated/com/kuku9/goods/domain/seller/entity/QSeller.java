package com.kuku9.goods.domain.seller.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSeller is a Querydsl query type for Seller
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeller extends EntityPathBase<Seller> {

    private static final long serialVersionUID = -1744127113L;

    public static final QSeller seller = new QSeller("seller");

    public final StringPath boardName = createString("boardName");

    public final StringPath domainNmae = createString("domainNmae");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduce = createString("introduce");

    public final StringPath phone_number = createString("phone_number");

    public QSeller(String variable) {
        super(Seller.class, forVariable(variable));
    }

    public QSeller(Path<? extends Seller> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSeller(PathMetadata metadata) {
        super(Seller.class, metadata);
    }

}

