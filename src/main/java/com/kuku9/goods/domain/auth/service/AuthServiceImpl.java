package com.kuku9.goods.domain.auth.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.auth.dto.*;
import com.kuku9.goods.domain.user.entity.*;
import com.kuku9.goods.domain.user.service.*;
import com.kuku9.goods.global.exception.*;
import com.kuku9.goods.global.security.jwt.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	@Override
	@Transactional
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
