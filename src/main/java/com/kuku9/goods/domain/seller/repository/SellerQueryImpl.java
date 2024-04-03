package com.kuku9.goods.domain.seller.repository;

import com.kuku9.goods.domain.order_product.entity.QOrderProduct;
import com.kuku9.goods.domain.product.entity.QProduct;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.QSeller;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SellerQueryImpl implements SellerQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<SoldProductResponse> getSoldProduct(
        Seller seller, LocalDate startDate, LocalDate endDate) {
        QSeller qSeller = QSeller.seller;
        QProduct qProduct = QProduct.product;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

        return jpaQueryFactory
            .select(Projections.constructor(SoldProductResponse.class,
                qProduct.name,
                qProduct.price,
                qProduct.quantity,
                qOrderProduct.quantity,
                qOrderProduct.quantity.multiply(qProduct.price)))
            .from(qSeller)
            .join(qProduct).on(qSeller.id.eq(qProduct.seller.id))
            .join(qOrderProduct).on(qProduct.id.eq(qOrderProduct.product.id))
            .where(qSeller.eq(seller)
                .and(qOrderProduct.createdAt.between(
                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())))
            .fetch();
    }

    @Override
    @Transactional(readOnly = true)
    public SoldProductSumPriceResponse getSoldProductSumPrice(
        Seller seller, LocalDate startDate, LocalDate endDate) {
        QSeller qSeller = QSeller.seller;
        QProduct qProduct = QProduct.product;
        QOrderProduct qOrderProduct = QOrderProduct.orderProduct;

//        return jpaQueryFactory
//            .select(qSeller.brandName,
//                ExpressionUtils.sum(qOrderProduct.quantity.multiply(qProduct.price)).as("totalPrice"))
//            .from(qSeller)
//            .join(qProduct).on(qSeller.id.eq(qProduct.seller.id))
//            .join(qOrderProduct).on(qProduct.id.eq(qOrderProduct.product.id))
//            .where(qOrderProduct.createdAt.between(
//                    startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())
//                .and(qSeller.id.eq(seller.getId())))
//            .groupBy(qSeller.brandName)
//            .fetch();

        return jpaQueryFactory
            .select(Projections.constructor(SoldProductSumPriceResponse.class,
                qSeller.brandName,
                qOrderProduct.quantity.multiply(qProduct.price).sum()))
            .from(qSeller)
            .join(qProduct).on(qSeller.id.eq(qProduct.seller.id))
            .join(qOrderProduct).on(qProduct.id.eq(qOrderProduct.product.id))
            .where(qSeller.eq(seller).and(qOrderProduct.createdAt.between(
                startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay())))
            .groupBy(qSeller.brandName)
            .fetchOne();
    }

}
