package com.kuku9.goods.domain.seller.service;

import com.kuku9.goods.domain.search.dto.ProductSearchResponse;
import com.kuku9.goods.domain.seller.dto.request.ProductRegistRequest;
import com.kuku9.goods.domain.seller.dto.request.ProductUpdateRequest;
import com.kuku9.goods.domain.seller.dto.response.SellerCheckResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductQuantityResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductResponse;
import com.kuku9.goods.domain.seller.dto.response.SoldProductSumPriceResponse;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.user.entity.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SellerService {

    /**
     * @param requestDto 상품 정보
     * @param user       로그인 유저
     * @return DB에 저장될 상품
     */
    String createProduct(ProductRegistRequest requestDto, User user);

    /**
     * @param productId 상품 고유 식별자
     * @param user      로그인 유저
     * @return 셀러 고유 식별자
     */
    String updateProductStatus(Long productId, User user);

    /**
     * @param productId  상품 고유 식별자
     * @param requestDto 상품 수정 정보
     * @param user       로그인 유저
     * @return 셀러 고유 식별자
     */
    String updateProduct(
        Long productId, ProductUpdateRequest requestDto, User user);

    /**
     *
     * @param productId 조회할 상품 고유 식별자
     * @param user 로그인 유저
     * @return
     */
    String deleteProduct(Long productId, User user);

    /**
     * @param user      로그인 유저
     * @param pageable  조회 시 부하를 감소하기 위한 페이지
     * @param startDate 조회할 시작 날짜
     * @param endDate   조회할 마지막 날짜
     * @return 판매된 상푸 정보
     */
    Page<SoldProductResponse> getSoldProduct(
        User user, Pageable pageable, LocalDate startDate, LocalDate endDate);

    /**
     * @param user      로그인 유저
     * @param startDate 조회할 시작 날짜
     * @param endDate   조회할 마지막 날짜
     * @return 판매 총 금액
     */
    SoldProductSumPriceResponse getSoldProductSumPrice(
        User user, LocalDate startDate, LocalDate endDate);

    /**
     * @param user      로그인 유저
     * @param startDate 조회할 시작 날짜
     * @param endDate   조회할 마지막 날짜
     * @return 판매된 상품 수량 상위 10개
     */
    List<SoldProductQuantityResponse> getSoldProductQuantityTopTen(
        User user, LocalDate startDate, LocalDate endDate);

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

    List<ProductSearchResponse> searchProductName(String keyword);

    List<ProductSearchResponse> searchProductIntroduce(String keyowrd);

    /**
     * 셀러인지 체크 - sellerId 반환
     *
     * @param user 유저
     * @return sellerId
     */
    SellerCheckResponse checkSeller(User user);

}
