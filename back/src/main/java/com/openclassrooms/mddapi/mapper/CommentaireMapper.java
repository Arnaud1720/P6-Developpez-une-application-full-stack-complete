package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import com.openclassrooms.mddapi.entity.Commentaire;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentaireMapper {
    // Entity -> DTO
    @Mapping(target = "postId",         source = "post.id")
    @Mapping(target = "auteurId",       source = "auteur.id")
    @Mapping(target = "auteurUsername", source = "auteur.username")
    CommentaireDto toDto(Commentaire entity);

    // DTO -> Entity
    @Mapping(target = "post.id",   source = "postId")
    @Mapping(target = "auteur.id", source = "auteurId")
    @Mapping(target = "createdAt", ignore = true)
    Commentaire toEntity(CommentaireDto dto);
}
