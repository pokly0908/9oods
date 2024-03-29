package com.kuku9.goods.domain.user.service;


import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.dto.response.UserResponse;
import com.kuku9.goods.domain.user.entity.User;
import java.nio.file.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

  /**
   * 회원가입
   *
   * @param request 회원가입을 위한 유저 정보
   */
  void signup(UserSignupRequest request);

  /**
   * 유저이름에 해당하는 유저 가져오기
   *
   * @param username 유저아이디, 유저이메일
   * @return User 유저
   */
  User findByUsername(String username);

  /**
   * 비밀번호 변경
   *
   * @param request 현재비밀번호, 신규비밀번호
   * @param user    유저
   */
  void modifyPassword(ModifyPasswordRequest request, User user);


  UserResponse getUserInfo(Long userId, User user) throws AccessDeniedException;
}
