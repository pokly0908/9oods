package com.kuku9.goods.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserSignupRequest {

    @Pattern(regexp = "[가-힣]+")
    String name;

    @Pattern(regexp = "^[a-z0-9]{6,14}$",
            message = "영어 소문자 및 숫자를 사용하여 6자이상 14자이하로 입력해주세요.")
    String username;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=]{8,15}$",
            message = "영어 소문자 및 대문자, 숫자, 특수문자를 사용하여 8자 이상 15자 이하로 입력해주세요.")
    String password;

    @Email
    String email;

    String adminCode;


}
