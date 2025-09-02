package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.ThemeDto;

import java.util.List;

public interface ThemeService {
    List<ThemeDto> getAllThemes();
    ThemeDto getThemeById(int id);
    ThemeDto save(ThemeDto themeDto);
}
