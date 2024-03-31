package com.kuku9.goods.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class RegisterSellerRequest {

    @NotNull(message = "브랜드 명칭을 꼭 입력해주세요.")
    private String brandName;

    @Pattern(regexp = "^[a-z0-9]{3,12}", message = "도메인 이름은 소문자로 3자이상 12자 이하로 입력해주세요.")
    private String domainName;

    @NotNull(message = "브랜드 소개글을 200자 이하로 적어주세요.")
    @Size(min = 10, max = 200)
    private String introduce;

    @Email(message = "판매자 연락용 이메일을 남겨주세요.")
    private String email;

    @Pattern(regexp = "^[0-9]", message = "-없이 판매자 연락용 전화번호를 남겨주세요.")
    private String phoneNumber;


}
