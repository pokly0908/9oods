package com.kuku9.goods.coupon;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.coupon.service.CouponService;
import com.kuku9.goods.domain.event.service.EventServiceImpl;
import java.time.LocalDate;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class CouponTest {

	@Test
	@DisplayName("50 수량의 선착순 쿠폰에 50명의 유저가 접근하는 상황 가정")
	void test() {
		Coupon testCoupon = new Coupon();
		ReflectionTestUtils.setField(
			testCoupon, "expirationDate", LocalDate.of(2024, 4, 5)
		);
		ReflectionTestUtils.setField(
			testCoupon, "quantity", 50
		);


		IntStream.range(0, 50)
			.forEach(i -> {
				testCoupon.decrease();
				System.out.println("남은 쿠폰의 수량: " + testCoupon.getQuantity());
			});
	}

}
