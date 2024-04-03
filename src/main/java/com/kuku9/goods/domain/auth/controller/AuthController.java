package com.kuku9.goods.domain.auth.controller;

import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.auth.service.AuthService;
import com.kuku9.goods.global.security.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
