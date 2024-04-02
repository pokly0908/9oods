package com.kuku9.goods.user.service;

import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.domain.user.service.UserServiceImpl;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Spy
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("회원가입 - 성공적인 회원가입")
    void testSignup_Success() {
        // Given
        String originalPassword = "aAa12345@@";
        String encodedPassword = passwordEncoder.encode(originalPassword);
        UserSignupRequest request = new UserSignupRequest(
                "김철수",
                "cheolsu44@naver.com",
                originalPassword
        );
        User user = User.from(request, encodedPassword);
        when(passwordEncoder.encode(originalPassword)).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        userService.signup(request);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("회원가입 - 이미 존재하는 사용자 이름")
    void testSignup_UsernameAlreadyExists() {
        // Given
        String originalPassword = "aAa12345@@";
        UserSignupRequest request = new UserSignupRequest(
                "existingUser",
                "cheolsu44@naver.com",
                originalPassword
        );
        when(userRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // When/Then
        assertThrows(DuplicatedException.class, () -> userService.signup(request));
        verify(userRepository, never()).save(any(User.class));
    }


    @Test
    @DisplayName("비밀번호 변경 - 성공")
    void testModifyPassword_Success() {
        //Given
        String originPassword ="aAa12345@@";
        String encodedOriginPassword = passwordEncoder.encode(originPassword);
        String newPassword = "aAa12345@!";
        User user = new User(1L,
                "cheolsu44@naver.com",
                "김철수",
                encodedOriginPassword,
                UserRoleEnum.USER
        );
       // userRepository.save(user);
        ModifyPasswordRequest request = new ModifyPasswordRequest(
                originPassword,
                newPassword);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(originPassword, user.getPassword())).thenReturn(true);

        //when
        userService.modifyPassword(request, user);

        //then
        verify(passwordEncoder).encode(newPassword);
        assertEquals(passwordEncoder.encode(newPassword), user.getPassword());
    }

    @Test
    @DisplayName("비밀번호 변경 - 이전 비밀번호 오류")
    void testModifyPassword_InvalidPrePassword() {
        // Given
        String originPassword = "aAa12345@@";
        String encodedOriginPassword = passwordEncoder.encode(originPassword);
        String newPassword = "aAa12345@!";
        User user = new User(1L,
                "cheolsu44@naver.com",
                "김철수",
                encodedOriginPassword,
                UserRoleEnum.USER
        );
        ModifyPasswordRequest request = new ModifyPasswordRequest(
                "aAa12345@!!", // 잘못된 이전 비밀번호
                newPassword);
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(request.getPrePassword(), user.getPassword())).thenReturn(false); // 이전 비밀번호 검증 실패

        // When/Then
        assertThrows(InvalidPasswordException.class, () -> userService.modifyPassword(request, user));
    }

}
