package com.kuku9.goods.domain.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@AllArgsConstructor
public class ModifyPasswordRequest {

	@NotNull(message = "현재비밀번호를 입력해주세요.")
	private String prePassword;

	@Pattern(regexp = "^[a-zA-Z0-9!@#$%^&*()-_+=]{8,15}$",
		message = "새로운 비밀번호를 영어 소문자 및 대문자, 숫자, 특수문자를 사용하여 8자 이상 15자 이하로 입력해주세요.")
	private String newPassword;


}
