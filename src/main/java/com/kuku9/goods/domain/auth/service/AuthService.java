package com.kuku9.goods.domain.auth.service;


import com.kuku9.goods.domain.auth.dto.LoginRequest;

public interface AuthService {

	String login(LoginRequest request);
}
