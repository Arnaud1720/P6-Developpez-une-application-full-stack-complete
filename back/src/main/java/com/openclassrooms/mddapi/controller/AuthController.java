package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.entity.RefreshToken;
import com.openclassrooms.mddapi.service.RefreshTokenService;
import com.openclassrooms.mddapi.service.impl.UserPrincipal;
import com.openclassrooms.mddapi.utils.JwtUtils;
import com.openclassrooms.mddapi.utils.LoginRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authMgr;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService rtService;

    public AuthController(AuthenticationManager authMgr, JwtUtils jwtUtils, RefreshTokenService rtService) {
        this.authMgr = authMgr;
        this.jwtUtils = jwtUtils;
        this.rtService = rtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req, HttpServletResponse resp) {
        Authentication auth = authMgr.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsernameOrEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        String accessToken = jwtUtils.generateAccessToken(  userDetails.getUsername());
        RefreshToken rt = rtService.createRefreshToken(auth.getName());

        ResponseCookie cookie = ResponseCookie.from("refreshToken", rt.getToken())
                .httpOnly(true).path("/api/auth/refresh").maxAge(30L*24*3600).build();
        resp.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(Map.of("accessToken", accessToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@CookieValue("refreshToken") String rtCookie, HttpServletResponse resp) {
        return rtService.findByToken(rtCookie)
                .flatMap(rtService::verifyExpiration)
                .map(rt -> {
                    String newAccess = jwtUtils.generateAccessToken(rt.getUser().getUsername());
                    RefreshToken newRt = rtService.createRefreshToken(rt.getUser().getUsername());
                    // remplacer l’ancien cookie
                    ResponseCookie cookie = ResponseCookie.from("refreshToken", newRt.getToken())
                            .httpOnly(true).path("/api/auth/refresh").maxAge(30L*24*3600).build();
                    resp.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
                    return ResponseEntity.ok(Map.of("accessToken", newAccess));
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Refresh token invalid"));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @CookieValue(value = "refreshToken", required = false) String rtCookie,
            HttpServletResponse resp
    ) {
        if (rtCookie != null && !rtCookie.isBlank()) {
            rtService.findByToken(rtCookie)
                    .ifPresent(rt -> rtService.deleteByUser(rt.getUser()));
        }
        ResponseCookie cookie = ResponseCookie.from("refreshToken","")
                .httpOnly(true)
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();
        resp.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return ResponseEntity.noContent().build();
    }
    /**
     * GET  /api/users/profil
     * Récupère les infos du profil de l’utilisateur connecté
     */
    @GetMapping("/profil")
    public ResponseEntity<UserDto> getProfil(
            @AuthenticationPrincipal UserPrincipal principal
    ) {
        UserDto dto = principal.toDto();
        return ResponseEntity.ok(dto);
    }
}
