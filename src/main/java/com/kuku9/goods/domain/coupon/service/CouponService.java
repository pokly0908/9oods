package com.kuku9.goods.domain.coupon.service;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.user.entity.User;
import java.time.LocalDateTime;

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

    /**
     * 선착순 쿠폰 발행
     *
     * @param couponId 쿠폰 ID
     * @param user     유저
     * @param now      현재 시점
     */
    void issueCoupon(Long couponId, User user, LocalDateTime now);

}
