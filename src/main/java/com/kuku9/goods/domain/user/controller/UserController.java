package com.kuku9.goods.domain.user.controller;

import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(
            @Valid @RequestBody UserSignupRequest request,
           BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return handleValidationResult(bindingResult);
        }

        userService.signup(request);

        return ResponseEntity.created(URI.create("/api/v1/auth/login")).build();
    }

    private ResponseEntity<String> handleValidationResult(
            BindingResult bindingResult
    ) {
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            return ResponseEntity.badRequest()
                    .body(fieldError.getDefaultMessage());
        }
        throw new RuntimeException("예기치 못한 오류가 발생했습니다.");
    }


}
