package com.kuku9.goods.domain.event.controller;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.service.EventService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<String> createEvent(@Valid @RequestBody EventRequest request,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long eventId = eventService.createEvent(request, customUserDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/events/" + eventId)).build();
    }

    @PutMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<String> updateEvent(
        @Valid @RequestBody EventUpdateRequest request,
        @PathVariable Long eventId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Long updatedEventId = eventService.updateEvent(eventId, request,
            customUserDetails.getUser());

        return ResponseEntity.created(URI.create("/api/v1/events/" + updatedEventId)).build();
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventResponse> getEvent(@PathVariable Long eventId) {

        EventResponse eventResponse = eventService.getEvent(eventId);

        return ResponseEntity.ok().body(eventResponse);
    }

    @GetMapping
    public ResponseEntity<Page<EventResponse>> getAllEvents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "createdAt"));
        Page<EventResponse> eventResponses = eventService.getAllEvents(pageable);

        return ResponseEntity.ok().body(eventResponses);
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyRole('ROLE_SELLER')")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        eventService.deleteEvent(eventId, customUserDetails.getUser());

        return ResponseEntity.ok().build();
    }

	@DeleteMapping("/event-products/{eventProductId}")
	@PreAuthorize("hasAnyRole('ROLE_SELLER')")
	public ResponseEntity<Void> deleteEventProduct(
		@PathVariable Long eventProductId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

        eventService.deleteEventProduct(eventProductId, customUserDetails.getUser());

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{eventId}/coupons/{couponId}/issued-coupons")
    @PreAuthorize("hasAnyRole('ROLE_USER')")
    public ResponseEntity<Void> issueCoupon(
        @PathVariable Long eventId, @PathVariable Long couponId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        eventService.issueCoupon(eventId, couponId, customUserDetails.getUser(),
            LocalDateTime.now());

        log.info(String.format("쿠폰이 발행되었습니다."));
        return ResponseEntity.ok().build();
    }

}
