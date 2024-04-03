package com.kuku9.goods.global.exception;

public class DuplicatedException extends ApiException {

	public DuplicatedException(ExceptionStatus ex) {
		super(ex);
	}
}
