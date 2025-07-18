package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;
import com.openclassrooms.mddapi.service.PostService;
import com.openclassrooms.mddapi.service.impl.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(
            @AuthenticationPrincipal UserPrincipal principal,
            @Valid @RequestBody PostCreation dto
    ) {
        PostDto created = postService.create(dto, principal.getUser().getId());
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/subjects/{subjectId}/posts/{postId}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Integer subjectId,
            @PathVariable Integer postId
    ) {
        PostDto dto = postService.getPostBySubjetIdAndPostId(postId, subjectId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = postService.findAll();
        return postDtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(postDtos);
    }

    @GetMapping("/orderBy")
    public List<PostDto> getPosts(
            @RequestParam(name="asc", defaultValue="false") boolean asc
    ) {
        return postService.getPostsOrderByDate(asc);
    }
}
