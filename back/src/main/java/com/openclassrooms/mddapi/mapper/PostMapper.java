package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId",       source = "userid.id")
    @Mapping(target = "authorPseudo", source = "userid.username")
    // AJOUT des mappings pour le thème - utiliser subjectName si topicName n'existe pas
    @Mapping(target = "subjectId",   source = "theme.id")
    @Mapping(target = "subjectName", source = "theme.title")
    PostDto toDto(Post post);

    @Mapping(target = "userid.id", source = "userId")
    // AJOUT du mapping pour le thème
    @Mapping(target = "theme.id", source = "subjectId")
    Post toEntity(PostDto dto);
}
