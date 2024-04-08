package com.kuku9.goods.global.event;

import com.kuku9.goods.domain.user.entity.User;
import lombok.Getter;

@Getter
public class SignupEvent {

	private User user;

	public SignupEvent(User user) {
		this.user = user;
	}
}
