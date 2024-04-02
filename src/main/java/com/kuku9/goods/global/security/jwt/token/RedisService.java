package com.kuku9.goods.global.security.jwt.token;

import java.util.*;
import java.util.concurrent.*;
import lombok.*;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Component
@RequiredArgsConstructor
public class RedisService {

	private final RedisTemplate redisTemplate;

	public void setValues(String key, String data, Long ttl) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		values.set(key, data, ttl);
	}


	@Transactional(readOnly = true)
	public String getValues(String key) {
		ValueOperations<String, Object> values = redisTemplate.opsForValue();
		if (values.get(key) == null) {
			return "false";
		}
		return (String) values.get(key);
	}

	public void deleteValues(String key) {
		redisTemplate.delete(key);
	}

	public void expireValues(String key, int timeout) {
		redisTemplate.expire(key, timeout, TimeUnit.MILLISECONDS);
	}

	public void setHashOps(String key, Map<String, String> data) {
		HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
		values.putAll(key, data);
	}

	@Transactional(readOnly = true)
	public String getHashOps(String key, String hashKey) {
		HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
		return Boolean.TRUE.equals(values.hasKey(key, hashKey))
			? (String) redisTemplate.opsForHash().get(key, hashKey) : "";
	}

	public void deleteHashOps(String key, String hashKey) {
		HashOperations<String, Object, Object> values = redisTemplate.opsForHash();
		values.delete(key, hashKey);
	}

	public boolean checkExistsValue(String value) {
		return !value.equals("false");
	}
}
