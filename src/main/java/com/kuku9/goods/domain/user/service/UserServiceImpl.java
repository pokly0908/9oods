package com.kuku9.goods.domain.user.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.DUPLICATED_USERNAME;
import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_ADMIN_CODE;
import static com.kuku9.goods.global.exception.ExceptionStatus.INVALID_PASSWORD;
import static com.kuku9.goods.global.exception.ExceptionStatus.NOT_EQUAL_USER_ID;
import static com.kuku9.goods.global.exception.ExceptionStatus.NO_SUCH_USER;

import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.dto.response.UserResponse;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidAdminCodeException;
import com.kuku9.goods.global.exception.InvalidPasswordException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  @Value("${admin.code}")
  private String adminCode;

  @Override
  @Transactional
  public void signup(UserSignupRequest request) {
    checkIfUsernameAlreadyExists(request.getUsername());
    String encodedPassword = passwordEncoder.encode(request.getPassword());
    UserRoleEnum roleEnum = UserRoleEnum.USER;
    String adminCodeValue = null;

    if (!request.getAdminCode().isEmpty()) {
      checkIfAdminCodeEqual(request.getAdminCode());
      roleEnum = UserRoleEnum.ADMIN;
      adminCodeValue = request.getAdminCode();

    }
    User user = User.from(request, encodedPassword, roleEnum, adminCodeValue);
    userRepository.save(user);

  }

  @Override
  @Transactional
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElseThrow(
        () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
    );
  }

  @Override
  @Transactional
  public void modifyPassword(ModifyPasswordRequest request, User user) {
    User findUser = findByUsername(user.getUsername());

    if (!passwordEncoder.matches(request.getPrePassword(), findUser.getPassword())) {
      throw new InvalidPasswordException(INVALID_PASSWORD);
    }

    findUser.modifyPassword(passwordEncoder.encode(request.getNewPassword()));

  }

  @Override
  @Transactional(readOnly = true)
  public UserResponse getUserInfo(Long userId, User user) throws AccessDeniedException {
    User findUser = findById(userId);
    if (!user.getId().equals(userId)) {
      throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
    }
    return UserResponse.from(findUser);
  }

  private User findById(Long userId) {
    return userRepository.findById(userId).orElseThrow(
        () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
    );
  }

  private void checkIfAdminCodeEqual(String requestAdminCode) throws InvalidAdminCodeException {
    if (!requestAdminCode.equals(adminCode)) {
      throw new InvalidAdminCodeException(INVALID_ADMIN_CODE);
    }
  }

  private void checkIfUsernameAlreadyExists(String username) {
    if (userRepository.existsByUsername(username)) {
      throw new DuplicatedException(DUPLICATED_USERNAME);
    }
  }
}
