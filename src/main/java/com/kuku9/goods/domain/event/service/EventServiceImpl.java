package com.kuku9.goods.domain.event.service;

import static com.kuku9.goods.global.exception.ExceptionStatus.*;

import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.event_product.dto.EventProductRequest;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.repository.EventProductRepository;
import com.kuku9.goods.domain.issued_coupon.entity.IssuedCoupon;
import com.kuku9.goods.domain.issued_coupon.repository.IssuedCouponRepository;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidAdminEventException;
import com.kuku9.goods.global.exception.InvalidCouponException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

	private final EventRepository eventRepository;
	private final EventQuery eventQuery;
	private final EventProductRepository eventProductRepository;
	private final ProductRepository productRepository;
	private final CouponRepository couponRepository;
	private final IssuedCouponRepository issuedCouponRepository;
	private final RedissonClient redissonClient;

	private static final String LOCK_KEY = "couponLock";


    @Transactional
    public Long createEvent(EventRequest eventRequest, User user) {

        Event event = new Event(eventRequest, user);
        Event savedEvent = eventRepository.save(event);

        if (eventRequest.getEventProducts() != null) {
            eventRequest.getEventProducts().stream()
                .map(EventProductRequest::getProductId)
                .map(productId -> productRepository.findById(productId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다.")))
                .map(product -> new EventProduct(savedEvent, product))
                .forEach(eventProductRepository::save);

        }

        if (eventRequest.getCouponId() != null) {
            Coupon coupon = findCoupon(eventRequest.getCouponId());

            savedEvent.addCoupon(coupon);
        }

        return savedEvent.getId();
    }

    @Transactional
    @CacheEvict(value = "eventCache", key = "#eventId")
    public Long updateEvent(Long eventId, EventUpdateRequest eventRequest, User user) {

        Event event = findEvent(eventId);

        if (!event.getUser().getId().equals(user.getId())) {
            throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
        }

        event.update(eventRequest);

        return event.getId();
    }


    @Cacheable(value = "eventCache", key = "#eventId", unless = "#result == null")
    public EventResponse getEvent(Long eventId) {

        Event event = findEvent(eventId);
        List<Long> eventProducts = eventQuery.getEventProducts(event.getId());
        return EventResponse.from(event, eventProducts);
    }

    @Transactional(readOnly = true)
    public Page<EventResponse> getAllEvents(Pageable pageable) {

        return eventRepository.findAll(pageable)
            .map(event -> {
                List<Long> eventProducts = eventQuery.getEventProducts(event.getId());
                return EventResponse.from(event, eventProducts);
            });
    }

    @Transactional
    public void deleteEvent(Long eventId, User user) {

        Event event = findEvent(eventId);

        if (!event.getUser().getId().equals(user.getId())) {
            throw new InvalidAdminEventException(INVALID_ADMIN_EVENT);
        }

        eventQuery.deleteEventProduct(eventId);
        eventRepository.delete(event);

    }

    @Transactional
    public void deleteEventProduct(Long eventProductId, User user) {

        EventProduct eventProduct = eventProductRepository.findById(eventProductId)
            .orElseThrow(() -> new IllegalArgumentException("해당 이벤트 상품은 존재하지 않습니다."));

        eventProductRepository.delete(eventProduct);
    }

	public void issueCoupon(Long eventId, Long couponId, User user, LocalDateTime now) {
		RLock lock = redissonClient.getFairLock(LOCK_KEY);
		try {
			boolean isLocked = lock.tryLock(10, 60, TimeUnit.SECONDS);
			if(isLocked) {
				try {
					if(issuedCouponRepository.existsByCouponIdAndUserId(couponId, user.getId())) {
						throw new InvalidCouponException(INVALID_COUPON);
					}
					Event event = findEvent(eventId);
					if(now.isBefore(event.getOpenAt())) {
						throw new InvalidCouponException(INVALID_COUPON);
					}

					Coupon coupon = findCoupon(couponId);
					if (coupon.getQuantity() <= 0) {
						throw new InvalidCouponException(INVALID_COUPON);
					}
					coupon.decrease();
					IssuedCoupon issuedCoupon = new IssuedCoupon(user, coupon);
					issuedCouponRepository.save(issuedCoupon);
				} finally {
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

    private Event findEvent(Long eventId) {
        return eventRepository.findById(eventId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    private Coupon findCoupon(Long couponId) {
        return couponRepository.findById(couponId)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }
}
