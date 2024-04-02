package com.kuku9.goods.domain.user.repository;

import com.kuku9.goods.domain.user.entity.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String Username);

	boolean existsByUsername(String username);
}
