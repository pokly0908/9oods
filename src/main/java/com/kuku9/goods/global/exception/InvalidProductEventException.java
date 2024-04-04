package com.kuku9.goods.global.exception;

public class InvalidProductEventException extends ApiException {

    public InvalidProductEventException(ExceptionStatus ex) {
        super(ex);
    }

}
