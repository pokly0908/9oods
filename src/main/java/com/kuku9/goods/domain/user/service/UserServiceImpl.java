package com.kuku9.goods.domain.user.service;

import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidAdimCodeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    @Value( "${admin.code}")
    private String adminCode;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void signup(UserSignupRequest request){
        checkIfUsernameAlreadyExists(request.getUsername());
        checkIfEmailAlreadyExists(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        UserRoleEnum roleEnum = UserRoleEnum.USER;
        String adminCodeValue = null;

        if(!request.getAdminCode().isEmpty()){
            checkIfAdminCodeEqual(request.getAdminCode());
            roleEnum = UserRoleEnum.ADMIN;
            adminCodeValue = request.getAdminCode();

        }
        User user = User.from(request, encodedPassword, roleEnum, adminCodeValue);
        userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                ()-> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
        );
    }

    private void checkIfAdminCodeEqual(String requestAdminCode) throws InvalidAdimCodeException {
        if(!requestAdminCode.equals(adminCode)){
            throw new InvalidAdimCodeException(INVALID_ADMIN_CODE);
        }
    }

    private void checkIfEmailAlreadyExists(String email) {
        if(userRepository.existsByEmail(email)){
            throw new DuplicatedException(DUPLICATED_EMAIL);
        }
    }

    private void checkIfUsernameAlreadyExists(String username) {
        if(userRepository.existsByUsername(username)){
            throw new DuplicatedException(DUPLICATED_USERNAME);
        }
    }
}
