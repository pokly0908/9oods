package com.kuku9.goods.domain.user.service;


import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    /**
     * @param request 회원가입을 위한 유저 정보
     */
    void signup(UserSignupRequest request);

    /**
     * 유저이름에 해당하는 유저 가져오기
     * @param username 유저 이름
     * @return User 유저
     */
    User findByUsername(String username);
}
