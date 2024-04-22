package com.kuku9.goods.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@AllArgsConstructor
public class LoginRequest {

    @Email
    String email;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=]{8,15}$",
        message = "영어 소문자 및 대문자, 숫자, 특수문자를 사용하여 8자 이상 15자 이하로 입력해주세요.")
    String password;


}
