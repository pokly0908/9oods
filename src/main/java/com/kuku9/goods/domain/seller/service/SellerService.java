package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellProductStatisticsResponse;
import com.kuku9.goods.domain.seller.dto.response.SellProductResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;

public interface SellerService {

    // todo :: Parameter 주석 설명 추가하기
    Long createProduct(ProductRegistRequest requestDto, User user);

    Long orderProductStatus(Long productsId, User user);

    Long updateProduct(
        Long productId, ProductUpdateRequest requestDto, User user);

    List<SellProductResponse> getSellingProduct(
        User user, LocalDate startDate, LocalDate endDate);

    SellProductStatisticsResponse getSellProductStatistics(User user);

    /**
     * 셀러 db 저장
     *
     * @param seller
     * @return db에 저장된셀러 Seller
     */
    Seller save(Seller seller);

    /**
     * 유저가 셀러등록이 존재하는지 체크
     *
     * @param userId 유저아이디
     * @return true, false
     */
    Boolean checkSellerExistsByUserId(Long userId);

    /**
     * 브랜드 이름이 존재하는지 검사
     *
     * @param brandName 브랜드 이름
     * @return true, false
     */
    Boolean checkBrandNameExist(String brandName);

    /**
     * 도메인 이름이 존재하는지 검사
     *
     * @param domainName 도메인 이름
     * @return true, false
     */
    Boolean checkDomainNameExist(String domainName);

    /**
     * 셀러 이메일이 존재하는지 검사
     *
     * @param email 셀러 이메일
     * @return true, false
     */
    Boolean checkEmailExist(String email);

    /**
     * 셀러 전화번호가 이미 등록되어 있는지 검사
     *
     * @param phoneNumber 셀러 전화번호
     * @return true, false
     */
    Boolean checkPhoneNumberExist(String phoneNumber);

}
