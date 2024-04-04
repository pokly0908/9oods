package com.kuku9.goods.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    DUPLICATED_USERNAME(HttpStatus.BAD_REQUEST.value(), "해당 이메일로 가입한 유저가 존재합니다."),
    INVALID_ADMIN_CODE(HttpStatus.BAD_REQUEST.value(), "관리자 코드가 일치하지않습니다."),
    NO_SUCH_USER(HttpStatus.BAD_REQUEST.value(), "해당하는 유저 이름의 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치 하지 않습니다."),
    NOT_EQUAL_USER_ID(HttpStatus.BAD_REQUEST.value(), "해당 정보는 유저본인만 확인할 수 있습니다."),
    DUPLICATED_SELLER(HttpStatus.BAD_REQUEST.value(), "셀러 등록은 중복으로 할 수 없습니다."),
    NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재하지 않습니다."),
    INVALID_ADMIN_EVENT(HttpStatus.BAD_REQUEST.value(), "이벤트에 접근할 권한이 아닙니다."),
    INVALID_COUPON(HttpStatus.BAD_REQUEST.value(), "현재 쿠폰에 접근할 수 없습니다."),
    INVALID_SELLER_EVENT(HttpStatus.BAD_REQUEST.value(), "셀러만 상품을 등록할 수 있습니다. 셀러 신청하세요.");

    private final Integer statusCode;
    private final String message;
}
