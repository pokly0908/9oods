package com.kuku9.goods.domain.event.controller;

import com.kuku9.goods.domain.event.dto.EventRequest;
import com.kuku9.goods.domain.event.dto.EventResponse;
import com.kuku9.goods.domain.event.dto.EventTitleResponse;
import com.kuku9.goods.domain.event.dto.EventUpdateRequest;
import com.kuku9.goods.domain.event.service.EventService;
import com.kuku9.goods.global.security.CustomUserDetails;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public ResponseEntity<List<EventTitleResponse>> getEventTitles() {

        List<EventTitleResponse> eventResponses = eventService.getEventTitles();

        return ResponseEntity.ok().body(eventResponses);
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long eventId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        eventService.deleteEvent(eventId, customUserDetails.getUser());

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/eventProducts/{eventProductId}")
    public ResponseEntity<Void> deleteEventProduct(@PathVariable Long eventProductId,
        @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        eventService.deleteEventProduct(eventProductId, customUserDetails.getUser());

        return ResponseEntity.ok().build();
    }

}
