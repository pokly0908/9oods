package com.kuku9.goods.domain.search.repository;

import com.kuku9.goods.domain.product.entity.QProduct;
import com.kuku9.goods.domain.search.dto.SearchResponse;
import com.kuku9.goods.domain.seller.entity.QSeller;
import com.kuku9.goods.global.exception.ExceptionStatus;
import com.kuku9.goods.global.exception.NotFoundException;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.lang.annotation.ElementType;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SearchQueryImpl implements SearchQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    @Transactional(readOnly = true)
    public List<SearchResponse> search(String searchName) {
        QSeller qSeller = QSeller.seller;
        QProduct qProduct = QProduct.product;

        if (searchName == null || searchName.isEmpty()) {
            throw new NotFoundException(ExceptionStatus.NOT_FOUND_SEARCH_KEYWORD);
        }

        List<SearchResponse> searchProductName = jpaQueryFactory
            .select(Projections.constructor(
                SearchResponse.class,
                qProduct.name))
            .from(qProduct)
            .where(qProduct.name.like("%" + searchName + "%"))
            .fetch();

        List<SearchResponse> searchBrandName = jpaQueryFactory
            .select(Projections.constructor(
                SearchResponse.class,
                qSeller.brandName))
            .from(qSeller)
            .where(qSeller.brandName.like("%" + searchName + "%"))
            .fetch();

        if (!searchProductName.isEmpty()) {
            return searchProductName;
        } else if (!searchBrandName.isEmpty()) {
            return searchBrandName;
        } else {
            throw new NotFoundException(ExceptionStatus.NOT_FOUND_SEARCH_KEYWORD);
        }
    }

}
