package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.service.SubscriptionService;
import com.openclassrooms.mddapi.service.impl.UserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;

    /**
     * Crée une nouvelle souscription pour un userId / subjectId donnés.
     * Ex :
     * POST /api/subscriptions
     * {
     *   "userId": 1,
     *   "subjectId": 42
     * }
     */
    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionDto> addSubscription(
            @Valid @RequestBody SubscriptionDto dto
    ) {
        SubscriptionDto created = subscriptionService.addSubscription(dto);

        // Construit l'URI : /api/subscriptions/{id}
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @DeleteMapping("/{subjectId}/unsebscribe")
    public ResponseEntity<Void> unsubscribe(
            @AuthenticationPrincipal UserPrincipal principal,
            @PathVariable Integer subjectId
    ) {
        Integer userId = principal.getUser().getId();
        subscriptionService.deleteByUserAndSubject(userId, subjectId);
        return ResponseEntity.noContent().build();
    }
}
