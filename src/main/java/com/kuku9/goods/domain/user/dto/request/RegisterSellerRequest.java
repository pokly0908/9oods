package com.kuku9.goods.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

@Builder
@Getter
@Value
@AllArgsConstructor
public class RegisterSellerRequest {

    @Pattern(regexp = "^[a-zA-Z0-9가-힣_]{2,50}$",
        message = "올바른 브랜드 이름 형식이 아닙니다. 소문자 대문자 숫자 '_' 을 이용하여 브랜드 이름을 입력해주세요.")
    String brandName;

    @Pattern(regexp = "^[a-z0-9]{3,12}$",
        message = "도메인 이름은 소문자로 3자이상 12자 이하로 입력해주세요.")
    String domainName;

    @NotNull(message = "브랜드 소개글을 200자 이하로 적어주세요.")
    @Size(min = 10, max = 200)
    String introduce;

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$",
        message = "판매자 문의용 이메일을 입력해주세요.")
    String email;

    @Pattern(regexp = "^01(?:0|1|[6-9])\\d{7,8}$",
        message = "-없이 판매자 연락용 전화번호를 입력해주세요.")
    String phoneNumber;


}
