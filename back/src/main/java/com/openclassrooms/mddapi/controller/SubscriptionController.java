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
import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {
    private final SubscriptionService subscriptionService;


    @PostMapping("/subscribe")
    public ResponseEntity<SubscriptionDto> addSubscription(
            @Valid @RequestBody SubscriptionDto dto,
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        // Force l'userId à celui du principal (sécurité)
        dto.setUserId(principal.getUser().getId());

        SubscriptionDto created = subscriptionService.addSubscription(dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();

        return ResponseEntity.created(location).body(created);
    }

    /**
     * DELETE /api/subscriptions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeSubscription(
            @PathVariable Integer id
    ) {
        subscriptionService.removeSubscription(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<SubscriptionDto>> getMySubscriptions(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        Integer userId = principal.getUser().getId();
        List<SubscriptionDto> subscriptions = subscriptionService.findByUserId(userId);
        return ResponseEntity.ok(subscriptions);
    }
}