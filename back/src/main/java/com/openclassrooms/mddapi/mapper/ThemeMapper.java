package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.dto.ThemeDto;
import org.mapstruct.Mapper;
import com.openclassrooms.mddapi.entity.Theme;

@Mapper(componentModel = "spring")
public interface ThemeMapper {

    ThemeDto toDto(Theme theme);

    Theme toEntity(ThemeDto dto);
}
