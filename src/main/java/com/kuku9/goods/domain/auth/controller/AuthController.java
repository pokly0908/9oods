package com.kuku9.goods.domain.auth.controller;

import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.auth.service.AuthService;
import com.kuku9.goods.domain.user.service.UserService;
import com.kuku9.goods.security.jwt.JwtUtil;
import com.kuku9.goods.security.jwt.token.RedisService;
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

    private final RedisService redisService;
    private final AuthService authService;
    private final UserService userService;
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
