package com.kuku9.goods.domain.user.controller;

import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody UserSignupRequest request){
        userService.signup(request);

        return ResponseEntity.created(URI.create("/api/v1/auth/login")).build();
    }


}
