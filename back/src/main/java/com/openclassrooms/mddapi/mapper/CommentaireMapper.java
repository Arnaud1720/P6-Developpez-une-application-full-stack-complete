package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import com.openclassrooms.mddapi.entity.Commentaire;
@Mapper(componentModel = "spring")
public interface CommentaireMapper {

    CommentaireDto toDto(Commentaire commentaire);

    Post toEntity(CommentaireDto dto);
}
