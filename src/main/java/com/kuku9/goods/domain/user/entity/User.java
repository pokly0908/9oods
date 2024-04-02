package com.kuku9.goods.domain.user.entity;

import com.kuku9.goods.domain.user.dto.request.*;
import com.kuku9.goods.global.common.entity.*;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.*;

@AllArgsConstructor
@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Comment("사용자가 로그인할때 쓸 id명, 이메일")
    @Email
    private String username;

    @Column(nullable = false)
    @Comment("사용자 실명")
    private String realName;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;


    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Comment("유저 권한")
    private UserRoleEnum role = UserRoleEnum.USER;


    public User(
        UserSignupRequest request,
        String encodedPassword
    ) {
        this.username = request.getUsername();
        this.realName = request.getRealName();
        this.password = encodedPassword;

    }

    public static User from(
        UserSignupRequest request,
        String encodedPassword

    ) {
        return new User(request, encodedPassword);
    }

    public void modifyPassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

    public void updateRole(UserRoleEnum userRoleEnum) {
        this.role = userRoleEnum;
    }
}
