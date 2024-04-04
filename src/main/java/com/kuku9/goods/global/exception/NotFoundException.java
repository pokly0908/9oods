package com.kuku9.goods.global.exception;

public class NotFoundException extends ApiException {

    public NotFoundException(ExceptionStatus ex) {
        super(ex);
    }

}
