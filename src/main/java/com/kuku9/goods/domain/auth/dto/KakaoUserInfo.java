package com.kuku9.goods.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;

@Getter
@Value
@AllArgsConstructor
public class KakaoUserInfo {

    Long kakaoId;
    String nickname;
    String email;

    public static KakaoUserInfo from(Long kakaoId, String nickname, String email) {
        return new KakaoUserInfo(kakaoId, nickname, email);
    }
}
