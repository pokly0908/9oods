package com.kuku9.goods.global.concurrencycontrol;

import java.lang.reflect.Method;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j(topic = "distributedLockAspect")
@Aspect
@Component
@RequiredArgsConstructor
public class DistributedLockAspect {

	private static final String REDISSON_KEY_PREFIX = "LOCK::";

	private final RedissonClient redissonClient;
	private final TransactionAspect transactionAspect;

	@Around("@annotation(com.kuku9.goods.global.concurrencycontrol.DistributedLock)")
	public Object lock(final ProceedingJoinPoint joinPoint)
		throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		DistributedLock distributedLock = method.getAnnotation(DistributedLock.class);

		String key = REDISSON_KEY_PREFIX + CustomSpringELParser.getDynamicValue(
			signature.getParameterNames(), joinPoint.getArgs(), distributedLock.key());
		RLock rLock = redissonClient.getLock(key);  // (1)

		try {
			boolean available = rLock.tryLock(distributedLock.waitTime(),
				distributedLock.leaseTime(), distributedLock.timeUnit());  // (2)
			if (!available) {
				throw new IllegalArgumentException("Unable to acquire lock: " + key);
			}
			log.info("Lock acquired successfully");
			return transactionAspect.proceed(joinPoint);  // (3)
		} catch (InterruptedException e) {
			throw new InterruptedException();
		} finally {
			try {
				rLock.unlock();   // (4)
				log.info("Lock released successfully");
			} catch (IllegalMonitorStateException e) {
				log.error("Error while releasing lock", e);
			}
		}
	}
}
