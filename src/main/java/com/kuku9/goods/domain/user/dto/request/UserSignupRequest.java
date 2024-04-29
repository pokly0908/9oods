package com.kuku9.goods.domain.user.dto.request;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
@Value
@AllArgsConstructor
public class UserSignupRequest {

    @Pattern(regexp = "[가-힣]+", message = "실명은 한글만 입력할 수 있습니다.")
    String realName;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "이메일 형식에 맞게 입력해주세요.")
    String email;

    @Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=]{8,15}$",
        message = "영어 소문자 및 대문자, 숫자, 특수문자를 사용하여 8자 이상 15자 이하로 입력해주세요.")
    String password;


}
