package com.kuku9.goods.domain.auth.dto;

import lombok.*;

@Getter
@AllArgsConstructor
public class LoginRequest {

    private final String username;

    private final String password;


}
