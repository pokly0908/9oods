package com.kuku9.goods.domain.user.entity;

import com.kuku9.goods.common.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;


@Entity
@Getter
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Comment("사용자가 로그인할때 쓸 id")
    private String username;

    @Column(nullable = false)
    @Comment("사용자 실명")
    private String name;

    @Column(nullable = false, unique = true)
    @Comment("이메일")
    private String email;

    @Column(nullable = false)
    @Comment("비밀번호")
    private String password;

    @Column
    @Comment("관리자 회원가입시 사용할 코드")
    private String adminCode;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    @Comment("유저 권한")
    private UserRoleEnum role = UserRoleEnum.USER;


}
