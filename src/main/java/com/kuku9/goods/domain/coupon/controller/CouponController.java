package com.kuku9.goods.domain.coupon.controller;

import com.kuku9.goods.domain.coupon.dto.CouponRequest;
import com.kuku9.goods.domain.coupon.dto.CouponResponse;
import com.kuku9.goods.domain.coupon.service.CouponService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {

	private final CouponService couponService;

	@PostMapping
	@PreAuthorize("hasAnyRole('ROLE_SELLER')")
	public ResponseEntity<String> createCoupon(@Valid @RequestBody CouponRequest request) {
		Long couponId = couponService.createCoupon(request);

		return ResponseEntity.created(URI.create("/api/v1/events/" + couponId)).build();
	}

	@GetMapping("/{couponId}")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long couponId) {
		return ResponseEntity.ok().body(couponService.getCoupon(couponId));
	}

	@DeleteMapping("/{couponId}")
	@PreAuthorize("hasAnyRole('ROLE_SELLER')")
	public ResponseEntity<Void> deleteCoupon(@PathVariable Long couponId) {
		couponService.deleteCoupon(couponId);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{couponId}/issued-coupons")
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	public ResponseEntity<Void> issueCoupon(
		@PathVariable Long couponId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		couponService.issueCouponFromEvent(couponId, customUserDetails.getUser(),
			LocalDateTime.now());

		return ResponseEntity.ok().build();
	}
}
