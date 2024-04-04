package com.kuku9.goods.domain.coupon.service;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;

public interface CouponService {

    /**
     * 선착순 쿠폰 등록
     *
     * @param request 쿠폰 등록에 필요한 정보
     * @return 쿠폰 ID
     */
    Long createCoupon(CouponRequest request);

    /**
     * 선착순 쿠폰 조회
     *
     * @param couponId 쿠폰 ID
     * @return CouponResponse
     */
    CouponResponse getCoupon(Long couponId);

    /**
     * 선착순 쿠폰 삭제
     *
     * @param couponId 쿠폰 ID
     */
    void deleteCoupon(Long couponId);

}
