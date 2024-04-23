package com.kuku9.goods.domain.user.repository;

import com.kuku9.goods.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    Optional<User> findByKakaoId(Long kakaoId);
}
