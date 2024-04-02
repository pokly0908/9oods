package com.kuku9.goods.domain.user.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.seller.entity.*;
import com.kuku9.goods.domain.seller.service.*;
import com.kuku9.goods.domain.user.dto.request.*;
import com.kuku9.goods.domain.user.dto.response.*;
import com.kuku9.goods.domain.user.entity.*;
import com.kuku9.goods.domain.user.repository.*;
import com.kuku9.goods.global.exception.*;
import java.nio.file.*;
import java.util.*;
import lombok.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

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
    @Transactional(readOnly = true)
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

        if (!sellerService.isBrandNameUnique(request.getBrandName())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (!sellerService.isDomainNameUnique(request.getDomainName())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (!sellerService.isEmailUnique(request.getEmail())) {
            throw new DuplicatedException(DUPLICATED_SELLER);
        }

        if (!sellerService.isPhoneNumberUnique(request.getPhoneNumber())) {
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
