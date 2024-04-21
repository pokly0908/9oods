package com.kuku9.goods.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExceptionStatus {

    DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST.value(), "해당 이메일로 가입한 유저가 존재합니다."),
    NO_SUCH_USER(HttpStatus.BAD_REQUEST.value(), "해당하는 유저 이름의 유저를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST.value(), "비밀번호가 일치 하지 않습니다."),
    SAME_PASSWORD_NOW_AND_NEW(HttpStatus.BAD_REQUEST.value(), "현재 비밀번호와 같은 비밀번호로 변경할 수 없습니다."),
    NOT_EQUAL_USER_ID(HttpStatus.BAD_REQUEST.value(), "해당 정보는 유저본인만 확인할 수 있습니다."),
    DUPLICATED_SELLER(HttpStatus.BAD_REQUEST.value(), "이미 등록된 셀러정보가 있습니다."),
    NOT_FOUND(HttpStatus.BAD_REQUEST.value(), "존재하지 않습니다."),
    INVALID_ADMIN_EVENT(HttpStatus.BAD_REQUEST.value(), "이벤트에 접근할 권한이 아닙니다."),
    INVALID_COUPON(HttpStatus.BAD_REQUEST.value(), "현재 쿠폰에 접근할 수 없습니다."),
    INVALID_SELLER_EVENT(HttpStatus.BAD_REQUEST.value(), "셀러만 상품을 등록할 수 있습니다. 셀러 신청하세요."),
    INVALID_PRODUCT_EVENT(HttpStatus.BAD_REQUEST.value(), "해당 상품은 존재하지 않습니다."),
    DUPLICATED_COUPON(HttpStatus.BAD_REQUEST.value(), "이미 발급되었습니다."),
    BEFORE_EVENT_OPEN(HttpStatus.BAD_REQUEST.value(), "아직 오픈되지 않았습니다."),
    OUT_OF_STOCK_COUPON(HttpStatus.BAD_REQUEST.value(), "쿠폰이 모두 소진되었습니다."),
    NOT_FOUND_SEARCH_KEYWORD(HttpStatus.BAD_REQUEST.value(), "검색 결과가 없습니다.");

    private final Integer statusCode;
    private final String message;
}
