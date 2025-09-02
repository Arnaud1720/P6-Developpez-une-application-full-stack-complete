package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ProfilDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.service.impl.UserPrincipal;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String,Object>> save(@Valid @RequestBody UserDto userDto) {
        UserDto savedUser = userService.save(userDto);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.toString())
                .toUri();

        Map<String,Object> body = new HashMap<>();
        body.put("message", "User created !");
        body.put("user :", savedUser);

        return ResponseEntity
                .created(location)
                .body(body);
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAll() {
        List<UserDto> userDtos = userService.findAll();
        return ResponseEntity.ok(userDtos);
    }


    /**
     * PUT /api/users/update/profil
     * Met à jour le profil de l’utilisateur connecté
     */
    @PutMapping("/update/profil")
    public ResponseEntity<UserDto> updateProfil(
            @Valid @RequestBody UserDto incomingDto
    ) {
        // 1. Récupère l’ID du principal
        UserPrincipal principal = (UserPrincipal)
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        Integer myId = principal.getUser().getId();

        // 2. Force l’ID sur le DTO
        incomingDto.setId(myId);

        // 3. Appelle la bonne signature updateProfile(userId, dto)
        UserDto updated = userService.updateProfile(myId, incomingDto);

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ProfilDto> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal
    ) throws ChangeSetPersister.NotFoundException {
        ProfilDto profile = userService.getMyProfile(principal.getUser().getId());
        return ResponseEntity.ok(profile);
    }
}