package com.kuku9.goods.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.kuku9.goods.common.TestValue;
import com.kuku9.goods.domain.coupon.entity.Coupon;
import com.kuku9.goods.domain.coupon.repository.CouponRepository;
import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.entity.Event;
import com.kuku9.goods.domain.event.repository.EventQuery;
import com.kuku9.goods.domain.event.repository.EventRepository;
import com.kuku9.goods.domain.event.service.EventServiceImpl;
import com.kuku9.goods.domain.event_product.entity.EventProduct;
import com.kuku9.goods.domain.event_product.repository.EventProductRepository;
import com.kuku9.goods.domain.product.repository.ProductRepository;
import com.kuku9.goods.domain.user.entity.User;
import com.kuku9.goods.global.exception.InvalidAdminEventException;
import com.kuku9.goods.global.exception.NotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest extends TestValue {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private EventProductRepository eventProductRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CouponRepository couponRepository;
    @Mock
    private EventQuery eventQuery;
    @InjectMocks
    private EventServiceImpl eventService;


    private <T> void setEventUpdateDTO(T dto, String title, String content, LocalDateTime openAt) {
        ReflectionTestUtils.setField(dto, "title", title);
        ReflectionTestUtils.setField(dto, "content", content);
        ReflectionTestUtils.setField(dto, "openAt", openAt);
    }

	@Test
	@DisplayName("이벤트 등록 성공")
	void 이벤트_등록_성공() {
		// given
		User user = TEST_USER1;
		Coupon coupon = TEST_COUPON;
		EventRequest eventRequest = new EventRequest(TEST_EVENT_TITLE,
			TEST_EVENT_CONTENT,
			TEST_EVENT_OPEN,
			TEST_EVENT_EVENTPRODUCTS,
			TEST_COUPON_ID);

		Event event = TEST_EVENT;
		given(eventRepository.save(any())).willReturn(event);
		given(productRepository.findById(any())).willReturn(Optional.of(TEST_PRODUCT));
		given(couponRepository.findById(any())).willReturn(Optional.of(coupon));

        // when
        Long eventId = eventService.createEvent(eventRequest, user);

        // then
        assertEquals(TEST_EVENT_ID, eventId);
    }

	@Test
	@DisplayName("이벤트 등록 실패")
	void 이벤트_등록_실패() {
		// given
		User user = TEST_USER1;
		Coupon coupon = TEST_COUPON;
		EventRequest eventRequest = new EventRequest(TEST_EVENT_TITLE,
			TEST_EVENT_CONTENT,
			TEST_EVENT_OPEN,
			TEST_EVENT_EVENTPRODUCTS,
			TEST_COUPON_ID);

		Event event = TEST_EVENT;
		given(eventRepository.save(any())).willReturn(event);
		given(productRepository.findById(any())).willReturn(Optional.of(TEST_PRODUCT));

        // when-then
        assertThrows(NotFoundException.class, () -> {
            eventService.createEvent(eventRequest, user);
        });
    }

	@Test
	@DisplayName("이벤트 수정 성공")
	void 이벤트_수정_성공() {
		// given
		Event event = TEST_EVENT;
		User user = TEST_USER1;
		EventUpdateRequest eventUpdateRequest = new EventUpdateRequest("수정", "수정",
			LocalDateTime.now());

		given(eventRepository.findById(any())).willReturn(Optional.of(event));

        // when
        eventService.updateEvent(event.getId(), eventUpdateRequest, user);

        // then
        assertEquals("수정", event.getTitle());
        assertEquals("수정", event.getContent());
    }

	@Test
	@DisplayName("이벤트 수정 실패")
	void 이벤트_수정_실패() {
		// given
		Event event = TEST_EVENT;
		User user = TEST_USER2;
		EventUpdateRequest eventUpdateRequest = new EventUpdateRequest("수정", "수정",
			LocalDateTime.now());

		given(eventRepository.findById(any())).willReturn(Optional.of(event));

		// when-then
		assertThrows(InvalidAdminEventException.class, () -> {
			eventService.updateEvent(event.getId(), eventUpdateRequest, user);
		});
	}

	@Test
	@DisplayName("이벤트 삭제 성공")
	void 이벤트_삭제_성공() {
		// given
		Event event = TEST_EVENT;
		User user = TEST_USER1;
		given(eventRepository.findById(any())).willReturn(Optional.of(event));

		// when-then
		eventService.deleteEvent(event.getId(), user);
	}

	@Test
	@DisplayName("이벤트 삭제 실패")
	void 이벤트_삭제_실패() {
		// given
		Event event = TEST_EVENT;
		User user = TEST_USER2;
		given(eventRepository.findById(any())).willReturn(Optional.of(event));

		// when-then
		assertThrows(InvalidAdminEventException.class, () -> {
			eventService.deleteEvent(event.getId(), user);
		});
	}

	@Test
	@DisplayName("이벤트 상품 삭제 성공")
	void 이벤트_상품_삭제_성공() {
		// given
		EventProduct eventProduct = TEST_EVENTPRODUCT;
		User user = TEST_USER1;
		given(eventProductRepository.findById(any())).willReturn(Optional.of(eventProduct));

		// when-then
		eventService.deleteEventProduct(eventProduct.getId(), user);
	}

	@Test
	@DisplayName("이벤트 상품 삭제 실패")
	void 이벤트_상품_삭제_실패() {
		// given
		EventProduct eventProduct = TEST_EVENTPRODUCT;
		User user = TEST_USER1;
		given(eventProductRepository.findById(any())).willReturn(Optional.empty());

        // when-then
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.deleteEventProduct(eventProduct.getId(), user);
        });
    }
}
