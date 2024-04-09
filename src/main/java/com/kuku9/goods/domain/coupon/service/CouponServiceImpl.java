package com.kuku9.goods.domain.coupon.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_COUPON;
import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_FOUND;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponQuery;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponQuery;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.event.CouponEvent;
import com.kuku9.goods.global.exception.InvalidCouponException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	private final IssuedCouponQuery issuedCouponQuery;
	private final CouponRepository couponRepository;
	private final EventQuery eventQuery;
	private final IssuedCouponRepository issuedCouponRepository;
	private final RedissonClient redissonClient;
	private final ApplicationEventPublisher publisher;
	private final CouponQuery couponQuery;
	private static final String LOCK_KEY = "couponLock";

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

	public void issueCouponFromEvent(Long couponId, User user, LocalDateTime now) {
		RLock lock = redissonClient.getFairLock(LOCK_KEY);
		try {
			boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
			if (isLocked) {
				try {
					boolean isDuplicatedIssuance = issuedCouponRepository.existsByCouponIdAndUserId(
						couponId, user.getId());
					if (isDuplicatedIssuance) {
						throw new InvalidCouponException(INVALID_COUPON);
					}

					LocalDateTime openAt = eventQuery.getOpenDate(couponId);
					if (now.isBefore(openAt)) {
						throw new InvalidCouponException(INVALID_COUPON);
					}

					Coupon coupon = findCoupon(couponId);

					if (coupon.getQuantity() <= 0) {
						throw new InvalidCouponException(INVALID_COUPON);
					}
					coupon.decrease();
					couponRepository.save(coupon);
					IssuedCoupon issuedCoupon = new IssuedCoupon(user, coupon);
					issuedCouponRepository.save(issuedCoupon);
					log.info(
						String.format("쿠폰 발급 처리 [쿠폰 ID : %s]", issuedCoupon.getCoupon().getId()));
					publisher.publishEvent(
						new CouponEvent(issuedCoupon.getCoupon().getId(),
							issuedCoupon.getUser().getId()));
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void issueCoupon(User user) {
		RLock lock = redissonClient.getFairLock(LOCK_KEY);
		try {
			boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
			if (isLocked) {
				try {
					List<Coupon> suCouponIds = couponQuery.findByCategory("su");
					for (Coupon coupon : suCouponIds) {
						boolean isDuplicatedIssuance = issuedCouponRepository.existsByCouponIdAndUserId(
							coupon.getId(), user.getId());
						if (isDuplicatedIssuance) {
							throw new InvalidCouponException(INVALID_COUPON);
						}
						if (coupon.getQuantity() <= 0) {
							log.info(
								String.format("회원가입 쿠폰 수량 부족 [쿠폰 ID : %s | 유저 ID : %s]",
									coupon.getId(), user.getId())
							);
						} else {
							coupon.decrease();
							couponRepository.save(coupon);
							IssuedCoupon issuedCoupon = new IssuedCoupon(user, coupon);
							issuedCouponRepository.save(issuedCoupon);
							log.info(
								String.format("회원가입 쿠폰 발급 처리 [쿠폰 ID : %s]",
									issuedCoupon.getCoupon().getId()));
						}
					}
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Transactional
	public void useCoupon(Long issuedCouponId) {

		issuedCouponQuery.deleteCoupon(issuedCouponId);
	}
}
