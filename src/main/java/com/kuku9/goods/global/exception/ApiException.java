package com.kuku9.goods.global.exception;

public abstract class ApiException extends  RuntimeException{

    private final Integer statusCode;
    private final String message;

    protected ApiException(ExceptionStatus ex) {
        this.statusCode = ex.getStatusCode();
        this.message = ex.getMessage();
    }
}
