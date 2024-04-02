package com.kuku9.goods.domain.user.service;


import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.RegisterSellerRequest;
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

	/**
	 * 유저 정보 조회
	 *
	 * @param userId 조회할 유저 아이디
	 * @param user   유저
	 * @return user 정보
	 */
	UserResponse getUserInfo(Long userId, User user) throws AccessDeniedException;

	/**
	 * 셀러등록
	 *
	 * @param request 셀러 등록 정보
	 * @param user    셀러등록할 유저
	 * @return 셀러
	 */
	Seller registerSeller(RegisterSellerRequest request, User user);
}
