package com.kuku9.goods.domain.event.controller;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.service.EventService;
import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

	private final EventService eventService;

	@PostMapping
	public ResponseEntity<String> createEvent(@Valid @RequestBody EventRequest request) {

		Long eventId = eventService.createEvent(request.getTitle(), request.getContent(),
			request.getFileId());

		return ResponseEntity.created(URI.create("/api/v1/events" + eventId)).build();
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<String> updateEvent(@Valid @RequestBody EventRequest request,
		@PathVariable Long eventId) {

		Long updatedEventId = eventService.updateEvent(eventId, request.getTitle(),
			request.getContent(),
			request.getFileId());

		return ResponseEntity.created(URI.create("/api/v1/events" + updatedEventId)).build();
	}
}
