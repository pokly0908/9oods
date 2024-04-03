package com.kuku9.goods.domain.user.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.RegisterSellerRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.dto.response.UserResponse;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidPasswordException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerService sellerService;

    @Override
    @Transactional
    public void signup(UserSignupRequest request) {
        checkIfUsernameAlreadyExists(request.getUsername());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.from(request, encodedPassword);
        userRepository.save(user);

    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
            () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
        );
    }

    @Override
    @Transactional
    public void modifyPassword(ModifyPasswordRequest request, User user) {
        User findUser = findByUsername(user.getUsername());

        if (!passwordEncoder.matches(request.getPrePassword(), findUser.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD);
        }

        findUser.modifyPassword(passwordEncoder.encode(request.getNewPassword()));

    }

    @Override
    @Cacheable(value = "userCache", key = "#userId", unless = "#result == null")
    public UserResponse getUserInfo(Long userId, User user) throws AccessDeniedException {
        User findUser = findById(userId);
        if (!user.getId().equals(userId)) {
            throw new AccessDeniedException(String.valueOf(NOT_EQUAL_USER_ID));
        }
        return UserResponse.from(findUser);
    }

    @Override
    @Transactional
    public Seller registerSeller(RegisterSellerRequest request, User user) {

        if (sellerService.checkSellerExistsByUserId(user.getId())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (sellerService.checkBrandNameExist(request.getBrandName())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (sellerService.checkDomainNameExist(request.getDomainName())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (sellerService.checkEmailExist(request.getEmail())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (sellerService.checkPhoneNumberExist(request.getPhoneNumber())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        User findUser = findById(user.getId());

        findUser.updateRole(UserRoleEnum.SELLER);
        Seller seller = Seller.from(request, user);

        return sellerService.save(seller);
    }


    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
        );
    }

    private void checkIfUsernameAlreadyExists(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new DuplicatedException(DUPLICATED_USERNAME);
        }
    }

}
