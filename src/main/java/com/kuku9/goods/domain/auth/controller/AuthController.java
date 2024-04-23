package com.kuku9.goods.domain.auth.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.auth.service.AuthService;
import com.kuku9.goods.domain.auth.service.KakaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
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
    public ResponseEntity<Void> kakaologin(@RequestParam("code") final String code) throws JsonProcessingException {
        log.info("카카오 로그인 서비스 동작.");
        String accessToken = kakaoService.login(code);
        System.out.println(accessToken);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, accessToken)
            .build();

    }


}
