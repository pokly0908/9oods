package com.kuku9.goods.domain.auth.service;

import com.kuku9.goods.domain.auth.dto.LoginRequest;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.service.UserService;
import com.kuku9.goods.global.exception.InvalidPasswordException;
import com.kuku9.goods.security.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public String login(LoginRequest request) {
        User user = userService.findByUsername(request.getUsername());
        validateLoginPassword(request, user);
        return jwtUtil.createAccessToken(user.getUsername(), user.getRole());
    }

    private void validateLoginPassword(LoginRequest request, User user) {
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD);
        }
    }
}
