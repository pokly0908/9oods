package com.kuku9.goods.coupon;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.event.service.EventServiceImpl;
import com.kuku9.goods.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
//public class CouponTest {
//
//	@Autowired
//	private EventServiceImpl eventService;
//
//	@Autowired
//	private CouponRepository couponRepository;
//
//
//	@Test
//	@DisplayName("50 수량의 선착순 쿠폰에 50명의 유저가 접근하는 상황 가정")
//	void test() {
//
//		IntStream.range(0, 50).parallel()
//			.forEach(i -> {
//				Coupon coupon = couponRepository.findById(4L).orElseThrow();
//				coupon.decrease();
//				couponRepository.save(coupon);
//				System.out.println("남은 쿠폰의 수량: " + coupon.getQuantity());
//			});
//	}
//
//}
