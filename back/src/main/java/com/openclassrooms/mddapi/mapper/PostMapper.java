package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId",       source = "userid.id")
    @Mapping(target = "authorPseudo", source = "userid.username")
        // Ajoute ces deux lignes si l'entité Post a la relation topic/subject
        // @Mapping(target = "subjectId",   source = "subject.id")
        // @Mapping(target = "subjectName", source = "subject.name")
    PostDto toDto(Post post);

    @Mapping(target = "userid.id", source = "userId")
    Post toEntity(PostDto dto);
}
