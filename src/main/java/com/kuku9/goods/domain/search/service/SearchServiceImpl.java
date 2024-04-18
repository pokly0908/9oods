package com.kuku9.goods.domain.search.service;

import com.kuku9.goods.domain.search.document.ProductDocument;
import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.repository.ProductSearchRepository;
import com.kuku9.goods.domain.search.repository.SellerSearchRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private final ProductSearchRepository productSearchRepository;
    private final SellerSearchRepository sellerSearchRepository;

//    @Override
//    @Transactional(readOnly = true)
//    public List<ProductDocument> searchProductName(String keyword) {
//        return productSearchRepository.findAllByProductName(keyword);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<ProductDocument> searchProductIntroduce(String keyword) {
//        return productSearchRepository.findAllByIntroduce(keyword);
//    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDocument> searchProduct(String keyword) {
        return productSearchRepository.findAllByProductNameOrIntroduce(keyword);
    }

//    @Override
//    @Transactional(readOnly = true)
//    public List<SellerDocument> searchBrandName(String keyword) {
//        return sellerSearchRepository.findAllByBrandName(keyword);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<SellerDocument> searchBrandIntroduce(String keyword) {
//        return sellerSearchRepository.findAllByIntroduce(keyword);
//    }

    @Override
    @Transactional(readOnly = true)
    public List<SellerDocument> searchbrand(String keyword) {
        return sellerSearchRepository.findAllByBrandNameOrIntroduce(keyword);
    }

}
