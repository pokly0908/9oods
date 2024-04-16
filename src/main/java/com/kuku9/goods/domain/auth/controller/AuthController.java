package com.kuku9.goods.domain.auth.controller;

import static com.kuku9.goods.global.security.jwt.JwtUtil.BEARER_PREFIX;

import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @RequestBody LoginRequest request) {
        String accessToken = authService.login(request);


        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, BEARER_PREFIX + accessToken)
            .build();

    }

}
