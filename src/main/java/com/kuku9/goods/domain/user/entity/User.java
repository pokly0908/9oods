package com.kuku9.goods.domain.user.entity;

import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.global.common.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;


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
    private String email;

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

    @Column(nullable = false)
    @Comment("유저 생성 타입")
    @Enumerated(value = EnumType.STRING)
    private UserRegisterTypeEnum registerType = UserRegisterTypeEnum.LOCAL;

    @Comment(" 카카오 아이디")
    private Long kakaoId;


    public User(
        UserSignupRequest request,
        String encodedPassword
    ) {
        this.email = request.getEmail();
        this.realName = request.getRealName();
        this.password = encodedPassword;

    }

    public User(String email, String encodedPassword, String nickname, Long kakaoId) {
        this.email = email;
        this.password = encodedPassword;
        this.realName = nickname;
        this.registerType = UserRegisterTypeEnum.KAKAO;
        this.kakaoId = kakaoId;
    }

    public static User from(
        UserSignupRequest request,
        String encodedPassword

    ) {
        return new User(request, encodedPassword);
    }

    public static User fromKakao(
        String email,
        String encodedPassword,
        String nickname,
        Long kakaoId
    ) {

        return new User(email, encodedPassword, nickname, kakaoId);
    }

    public void modifyPassword(String encodedNewPassword) {
        this.password = encodedNewPassword;
    }

    public void updateRole(UserRoleEnum userRoleEnum) {
        this.role = userRoleEnum;
    }

    public User kakaoIdUpdate(Long kakaoId) {
        this.kakaoId = kakaoId;
        this.registerType = UserRegisterTypeEnum.KAKAO;
        return this;
    }
}
