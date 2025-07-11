package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.service.CommentaireService;
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
@RequestMapping("/api/posts/{postId}/comments")
public class CommentaireController {

    private final CommentaireService service;

    public CommentaireController(CommentaireService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<CommentaireDto> addComment(
            @PathVariable Integer postId,
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody CommentaireDto dto
    ) {
        CommentaireDto created = service.createComment(
                postId,
                principal.getUser().getId(),
                dto.getContenu()
        );
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping
    public List<CommentaireDto> getComments(@PathVariable Integer postId) {
        return service.listCommentsByPost(postId);
    }

}
