package com.kuku9.goods.domain.user.controller;

import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.dto.response.UserResponse;
import com.kuku9.goods.domain.user.service.UserService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.nio.file.AccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  ) {
    if (bindingResult.hasErrors()) {
      return handleValidationResult(bindingResult);
    }

    userService.signup(request);

    return ResponseEntity.created(URI.create("/api/v1/auth/login")).build();
  }

  @GetMapping("/{userId}")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SELLER')")
  public ResponseEntity<UserResponse> getUserInfo(
      @PathVariable Long userId,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) throws AccessDeniedException {
    UserResponse userResponse = userService.getUserInfo(userId, userDetails.getUser());

    return ResponseEntity.ok(userResponse);
  }

  @PatchMapping("/password")
  @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_SELLER')")
  public ResponseEntity<Void> modifyPassword(
      @RequestBody ModifyPasswordRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {
    userService.modifyPassword(request, userDetails.getUser());

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
