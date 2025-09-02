package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.ThemeDto;
import com.openclassrooms.mddapi.mapper.ThemeMapper;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.service.ThemeService;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.Theme;
import java.util.List;
@Service
public class ThemeServiceImpl implements ThemeService {

    private final ThemeRepository themeRepository;
    private final ThemeMapper themeMapper;

    public ThemeServiceImpl(ThemeRepository themeRepository, ThemeMapper themeMapper) {
        this.themeRepository = themeRepository;
        this.themeMapper = themeMapper;
    }

    @Override
    public List<ThemeDto> getAllThemes() {
        return themeRepository.
                findAll()
                .stream()
                .map(themeMapper::toDto).toList();
    }

    @Override
    public ThemeDto getThemeById(int id) {
        return null;
    }

    @Override
    public ThemeDto save(ThemeDto themeDto) {
        Theme theme = themeMapper.toEntity(themeDto);
        themeRepository.save(theme);
        return themeMapper.toDto(theme);
    }
}
