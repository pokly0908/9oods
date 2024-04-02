package com.kuku9.goods.domain.event.controller;

import com.kuku9.goods.domain.event.dto.*;
import com.kuku9.goods.domain.event.service.*;
import com.kuku9.goods.global.security.*;
import jakarta.validation.*;
import java.net.*;
import lombok.*;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

	private final EventService eventService;

	@PostMapping
	public ResponseEntity<String> createEvent(@Valid @RequestBody EventRequest request,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		Long eventId = eventService.createEvent(request, customUserDetails.getUser());

		return ResponseEntity.created(URI.create("/api/v1/events/" + eventId)).build();
	}

	@PutMapping("/{eventId}")
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
	public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		eventService.deleteEvent(eventId, customUserDetails.getUser());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/eventProducts/{eventProductId}")
	public ResponseEntity<Void> deleteEventProduct(
		@PathVariable Long eventProductId,
		@AuthenticationPrincipal CustomUserDetails customUserDetails) {

		eventService.deleteEventProduct(eventProductId, customUserDetails.getUser());

		return ResponseEntity.ok().build();
	}

}
