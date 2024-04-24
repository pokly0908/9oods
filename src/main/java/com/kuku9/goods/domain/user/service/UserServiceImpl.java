package com.kuku9.goods.domain.user.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.search.document.SellerDocument;
import com.kuku9.goods.domain.search.repository.SellerSearchRepository;
import com.kuku9.goods.domain.seller.entity.Seller;
import com.kuku9.goods.domain.seller.service.SellerService;
import com.kuku9.goods.domain.user.dto.request.ModifyPasswordRequest;
import com.kuku9.goods.domain.user.dto.request.RegisterSellerRequest;
import com.kuku9.goods.domain.user.dto.request.UserSignupRequest;
import com.kuku9.goods.domain.user.dto.response.UserResponse;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.entity.UserRoleEnum;
import com.kuku9.goods.domain.user.repository.UserRepository;
import com.kuku9.goods.global.event.SignupEvent;
import com.kuku9.goods.global.exception.DuplicatedException;
import com.kuku9.goods.global.exception.InvalidPasswordException;
import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j(topic = "유저서비스")
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SellerService sellerService;
    private final ApplicationEventPublisher publisher;
    private final SellerSearchRepository sellerSearchRepository;


    @Override
    @Transactional
    public void signup(UserSignupRequest request) {
        checkIfEmailAlreadyExists(request.getEmail());
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.from(request, encodedPassword);
        User savedUser = userRepository.save(user);
        log.info("회원가입 유저 아이디 : " + savedUser.getId());
        publisher.publishEvent(new SignupEvent(savedUser));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
            () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
        );
    }

    @Override
    @Transactional
    public void modifyPassword(ModifyPasswordRequest request, User user) {
        User findUser = findByEmail(user.getEmail());

        if (!passwordEncoder.matches(request.getPrePassword(), findUser.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD);
        }

        if (passwordEncoder.matches(request.getNewPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException(SAME_PASSWORD_NOW_AND_NEW);
        }

        findUser.modifyPassword(passwordEncoder.encode(request.getNewPassword()));

    }

    @Override
    @Cacheable(value = "userCache", key = "#user.id", unless = "#result == null")
    public UserResponse getUserInfo(User user) throws AccessDeniedException {
        User findUser = findById(user.getId());

        log.info("정보 조회 유저아이디 : " + findUser.getId());
        return UserResponse.from(findUser);
    }

    @Override
    @Transactional
    @CacheEvict(value = "userCache", key = "#user.id")
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

        log.info("셀러등록 셀러 아이디 : " + seller.getId());
        SellerDocument sellerDocument = new SellerDocument(
            findUser.getId(), request.getBrandName(), request.getIntroduce()
        );

        sellerSearchRepository.save(sellerDocument);

        return sellerService.save(seller);
    }


    private User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new NoSuchElementException(String.valueOf(NO_SUCH_USER))
        );
    }

    private void checkIfEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new DuplicatedException(DUPLICATED_EMAIL);
        }
    }

}
