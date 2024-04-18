package com.kuku9.goods.coupon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponQuery;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.coupon.service.CouponServiceImpl;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidCouponException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
public class CouponServiceImplTest extends TestValue {

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private EventQuery eventQuery;
    @Mock
    private IssuedCouponRepository issuedCouponRepository;
    @Mock
    private RedissonClient redissonClient;
    @Mock
    private ApplicationEventPublisher publisher;
    @Mock
    private CouponQuery couponQuery;
    @Mock
    private RLock lock;
    @InjectMocks
    private CouponServiceImpl couponService;

	@Test
	@DisplayName("쿠폰 등록 성공")
	void 쿠폰_등록_성공() {
		// given
		CouponRequest couponRequest = new CouponRequest(TEST_COUPON_EXPIRATIONDATE, TEST_COUPON_QUANTITY,
			TEST_COUPON_CATEGORY);

		Coupon coupon = TEST_COUPON;
		given(couponRepository.save(any())).willReturn(coupon);

        //when
        Long couponId = couponService.createCoupon(couponRequest);

        // then
        assertEquals(coupon.getId(), couponId);
    }

    @Test
    @DisplayName("쿠폰 삭제 성공")
    void 쿠폰_삭제_성공() {
        // given
        Coupon coupon = TEST_COUPON;
        given(couponRepository.findById(any())).willReturn(Optional.of(coupon));

        //when-then
        couponService.deleteCoupon(coupon.getId());
    }

    @Test
    @DisplayName("쿠폰 삭제 실패")
    void 쿠폰_삭제_실패() {
        // given
        Coupon coupon = TEST_COUPON;
        given(couponRepository.findById(any())).willReturn(Optional.empty());

        // when-then
        assertThrows(NotFoundException.class, () -> {
            couponService.deleteCoupon(coupon.getId());
        });
    }

    @Test
    @DisplayName("쿠폰 발급 성공")
    void 쿠폰_발급_성공() throws InterruptedException {
        // given
        Coupon coupon = TEST_COUPON;
        IssuedCoupon issuedCoupon = TEST_ISSUED_COUPON;
        User user = TEST_USER2;
        given(issuedCouponRepository.existsByCouponIdAndUserId(any(), any())).willReturn(false);
        given(eventQuery.getOpenDate(any())).willReturn(
            LocalDateTime.of(2024, 4, 7, 12, 30));
        given(couponRepository.findById(any())).willReturn(Optional.of(coupon));
        given(couponRepository.save(any())).willReturn(coupon);

        // when
        couponService.issueCouponFromEvent(coupon.getId(), user, LocalDateTime.now());

        // then
        assertEquals(49, coupon.getQuantity());
    }

    @Test
    @DisplayName("쿠폰 발급 실패")
    void 쿠폰_발급_실패() throws InterruptedException {
        // given
        Coupon coupon = TEST_COUPON;
        IssuedCoupon issuedCoupon = TEST_ISSUED_COUPON;
        User user = TEST_USER2;
        given(issuedCouponRepository.existsByCouponIdAndUserId(any(), any())).willReturn(true);

        // when-then
        assertThrows(InvalidCouponException.class, () -> {
            couponService.issueCouponFromEvent(coupon.getId(), user, LocalDateTime.now());
        });
    }

    @Test
    @DisplayName("회원가입 쿠폰 발급 성공")
    void 회원가입_쿠폰_발급_성공() throws InterruptedException {
        // given
        Coupon coupon = TEST_SIGNUP_COUPON;
        IssuedCoupon issuedCoupon = TEST_ISSUED_COUPON2;
        User user = TEST_USER2;
        given(couponQuery.findByCategory(any())).willReturn(List.of(coupon));
        given(issuedCouponRepository.existsByCouponIdAndUserId(any(), any())).willReturn(false);
        given(couponRepository.save(any())).willReturn(coupon);

        // when
        couponService.issueCoupon(user);

        // then
        assertEquals(49, coupon.getQuantity());
    }

    @Test
    @DisplayName("회원가입 쿠폰 발급 실패")
    void 회원가입_쿠폰_발급_실패() throws InterruptedException {
        // given
        Coupon coupon = TEST_SIGNUP_COUPON;
        IssuedCoupon issuedCoupon = TEST_ISSUED_COUPON2;
        User user = TEST_USER2;
        given(couponQuery.findByCategory(any())).willReturn(List.of(coupon));
        given(issuedCouponRepository.existsByCouponIdAndUserId(any(), any())).willReturn(true);

        // when-then
        assertThrows(InvalidCouponException.class, () -> {
            couponService.issueCoupon(user);
        });
    }
}
