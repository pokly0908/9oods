package com.kuku9.goods.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.auth.service.AuthService;
import com.kuku9.goods.domain.auth.service.KakaoService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final KakaoService kakaoService;



    @PostMapping("/login")
    public ResponseEntity<Void> login(
        @RequestBody LoginRequest request) {
        String accessToken = authService.login(request);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accessToken)
            .build();

    }

    @GetMapping("/kakao/callback")
    public ResponseEntity<Void> kakaologin(HttpServletRequest request) throws JsonProcessingException {
        String code = request.getParameter("code");
        String accessToken = kakaoService.login(code);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accessToken)
            .build();

    }



}
