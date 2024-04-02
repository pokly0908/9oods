package com.kuku9.goods.domain.user.dto.response;

import com.kuku9.goods.domain.user.entity.*;
import java.time.*;
import lombok.*;

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
        return new UserResponse(findUser.getRealName(), findUser.getRole(),
            findUser.getCreatedAt());
    }
}
