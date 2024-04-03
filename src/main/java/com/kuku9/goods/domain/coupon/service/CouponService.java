package com.kuku9.goods.domain.coupon.service;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;

public interface CouponService {

	/**
	 *
	 * @param request 쿠폰 등록에 필요한 정보
	 * @return        쿠폰 ID
	 */
	Long createCoupon(CouponRequest request);

	/**
	 *
	 * @param couponId 쿠폰 ID
	 * @return		   CouponResponse
	 */
	CouponResponse getCoupon(Long couponId);

	/**
	 *
	 * @param couponId 쿠폰 ID
	 */
	void deleteCoupon(Long couponId);
}
