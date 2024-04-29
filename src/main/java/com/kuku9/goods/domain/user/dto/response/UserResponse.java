package com.kuku9.goods.domain.user.dto.response;

import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import lombok.NoArgsConstructor;
import lombok.Value;


@Value
@NoArgsConstructor(force = true)
public class UserResponse {

    String realName;

    UserRoleEnum role;

    String createdAt;

    public UserResponse(String realName, UserRoleEnum role, String createdAt) {
        this.realName = realName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static UserResponse from(User findUser) {
        return new UserResponse(findUser.getRealName(), findUser.getRole(),
            findUser.getCreatedAt().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)));
    }
}
