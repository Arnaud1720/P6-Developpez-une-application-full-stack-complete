package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ProfilDto;
import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.RefreshToken;
import com.openclassrooms.mddapi.service.RefreshTokenService;
import com.openclassrooms.mddapi.service.UserService;
import com.openclassrooms.mddapi.service.impl.UserPrincipal;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final RefreshTokenService refreshTokenService;
    public UserController(UserService userService, RefreshTokenService refreshTokenService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
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
        // Récupère l’ID du principal pour éviter toute modification d’un autre user
        UserPrincipal principal =
                (UserPrincipal) SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getPrincipal();
        Integer myId = principal.getUser().getId();

        // On force l’ID dans le DTO avant de passer au service
        incomingDto.setId(myId);

        // Appelle ton service qui fera le findById + save
        UserDto updated = userService.save(incomingDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/me/profile")
    public ResponseEntity<ProfilDto> getMyProfile(
            @AuthenticationPrincipal UserPrincipal principal
    ) throws ChangeSetPersister.NotFoundException {
        ProfilDto profile = userService.getMyProfile(principal.getUser().getId());
        return ResponseEntity.ok(profile);
    }

//    @PostMapping("/logout")
//    public ResponseEntity<Void> logout(
//            @CookieValue(name = "refreshToken", required = false) String refreshToken,
//            HttpServletResponse response) {
//
//        // 1️⃣ Supprimer le refresh token de la BDD si présent
//        if (refreshToken != null) {
//            refreshTokenService.findByToken(refreshToken)
//                    .ifPresent(rt -> refreshTokenService.deleteByUser(rt.getUser()));
//        }
//
//        // 2️⃣ Envoyer un cookie vidé pour l’effacer côté client
//        ResponseCookie cookie = ResponseCookie.from("refreshToken", "")
//                .httpOnly(true)
//                .path("/api/auth/refresh")
//                .maxAge(0)
//                .build();
//        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        // 3️⃣ Statut 204 No Content
//        return ResponseEntity.noContent().build();
//    }

}