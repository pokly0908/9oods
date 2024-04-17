package com.kuku9.goods.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@AllArgsConstructor
@Value
public class LoginRequest {

    String username;

    String password;


}
