package com.kuku9.goods.domain.user.dto.response;

import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link com.kuku9.goods.domain.user.entity.User}
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class UserResponse {

    String realName;

    UserRoleEnum role;

    String createdAt;

    public static UserResponse from(User findUser) {
        return new UserResponse(findUser.getRealName(), findUser.getRole(),
            findUser.getCreatedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
    }
}
