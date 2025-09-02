package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.service.ThemeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/theme")
public class ThemeController {
    private final ThemeService themeService;

    public ThemeController(ThemeService themeService) {
        this.themeService = themeService;
    }
    @PostMapping("/save")
    public ResponseEntity<ThemeDto> save(@Valid @RequestBody ThemeDto dto) {
        ThemeDto created = themeService.save(dto);
        URI location =  ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(created.getId())
                .toUri();
                return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ThemeDto>> getAll() {
        List<ThemeDto> themeDtoList = themeService.getAllThemes();
        return ResponseEntity.ok(themeDtoList);
    }
}
