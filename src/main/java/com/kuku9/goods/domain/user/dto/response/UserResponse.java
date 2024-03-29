package com.kuku9.goods.domain.user.dto.response;

import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * DTO for {@link com.kuku9.goods.domain.user.entity.User}
 */
@AllArgsConstructor
@Getter
public class UserResponse {

  String realName;

  UserRoleEnum role;

  LocalDateTime createdAt;

  public static UserResponse from(User findUser) {
    return new UserResponse(findUser.getRealName(), findUser.getRole(), findUser.getCreatedAt());
  }
}
