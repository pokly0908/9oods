package com.kuku9.goods.global.exception;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j(topic = "예외 핸들링")
@RestControllerAdvice
public class GlobalExceptionHandler {

	public static final String INTERNAL_ERROR_500 = "서버 내부 오류가 발생했습니다. / Please Contact Admin";

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<String> handleAllUncaughtException(Exception ex) {
		log.error(ex.getLocalizedMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_ERROR_500);
	}

	@ExceptionHandler(ApiException.class)
	protected ResponseEntity<String> handleApiException(ApiException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
			.body(INTERNAL_ERROR_500);
	}

	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<String> invalidPasswordException(InvalidPasswordException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode())
			.body(ex.getMessage());

	}

	@ExceptionHandler(DuplicatedException.class)
	public ResponseEntity<String> duplicatedException(DuplicatedException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.status(ex.getStatusCode())
			.body(ex.getMessage());
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<String> noSuchElementException(NoSuchElementException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.badRequest()
			.body(ex.getMessage());
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<String> AccessDeniedException(AccessDeniedException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.badRequest()
			.body(ex.getMessage());
	}

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<String> EventNotFoundException(EventNotFoundException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.badRequest().body(ex.getMessage());
	}

	@ExceptionHandler(InvalidAdminEventException.class)
	public ResponseEntity<String> InvalidAdminEventException(InvalidAdminEventException ex) {
		log.error(ex.getMessage());
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
}
