package com.kuku9.goods.domain.user.entity;

import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;


import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;


@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
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
    ){
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
    ){
      return new User(request, encodedPassword, role, adminCodeValue);
    }

}
