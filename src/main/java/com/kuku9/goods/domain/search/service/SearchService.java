package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import java.util.List;

public interface SearchService {

    List<ProductDocument> searchProductName(String keyword);
//
    List<ProductDocument> searchProductIntroduce(String keyword);

    List<ProductDocument> searchProduct(String keyword);

    List<SellerDocument> searchBrandName(String keyword);
//
    List<SellerDocument> searchBrandIntroduce(String keyword);

    List<SellerDocument> searchBrand(String keyword);

}
