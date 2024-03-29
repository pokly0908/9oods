package com.kuku9.goods.domain.user.entity;

import com.kuku9.goods.common.entity.SecureBaseEntity;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user SET deleted_at=CURRENT_TIMESTAMP where id=?")
@SQLRestriction("deleted_at IS NULL")
public class User extends SecureBaseEntity {

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

    @Column
    @Comment("관리자 회원가입시 사용할 코드")
    private String adminCode;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Comment("유저 권한")
    private UserRoleEnum role;


    public User(
            UserSignupRequest request,
            String encodedPassword,
            UserRoleEnum role,
            String adminCodeValue
    ) {
        this.username = request.getUsername();
        this.realName = request.getRealName();
        this.password = encodedPassword;
        this.role = role;
        this.adminCode = adminCodeValue;

    }

    public static User from(
            UserSignupRequest request,
            String encodedPassword,
            UserRoleEnum role,
            String adminCodeValue
    ) {
        return new User(request, encodedPassword, role, adminCodeValue);
    }

    public void modifyPassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }
}
