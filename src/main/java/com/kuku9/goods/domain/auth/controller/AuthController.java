package com.kuku9.goods.domain.auth.controller;

import com.kuku9.goods.domain.auth.dto.*;
import com.kuku9.goods.domain.auth.service.*;
import com.kuku9.goods.global.security.jwt.*;
import jakarta.servlet.http.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final JwtUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<Void> login(
		@RequestBody LoginRequest request,
		HttpServletResponse response) {
		String token = authService.login(request);
		jwtUtil.accessTokenSetHeader(token, response);

		return ResponseEntity.ok().build();

	}

}
