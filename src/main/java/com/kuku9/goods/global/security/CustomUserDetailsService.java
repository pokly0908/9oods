package com.kuku9.goods.global.security;

import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.domain.user.repository.*;
import lombok.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
			.orElseThrow(() -> new UsernameNotFoundException("Not Found " + username));

		return new CustomUserDetails(user, user.getUsername(), user.getPassword());
	}
}
