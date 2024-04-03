package com.kuku9.goods.domain.coupon.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_FOUND;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final CouponRepository couponRepository;

	@Transactional
	public Long createCoupon(CouponRequest request) {
		Coupon coupon = new Coupon(request);
		Coupon savedCoupon = couponRepository.save(coupon);
		return savedCoupon.getId();
	}

	@Transactional(readOnly = true)
	public CouponResponse getCoupon(Long couponId) {
		Coupon coupon = findCoupon(couponId);
		return CouponResponse.from(coupon);
	}

	@Transactional
	public void deleteCoupon(Long couponId) {
		couponRepository.delete(findCoupon(couponId));
	}

	private Coupon findCoupon(Long couponId) {
		return couponRepository.findById(couponId)
			.orElseThrow(() -> new NotFoundException(NOT_FOUND));
	}
}
