package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId",    source = "author.id")
    @Mapping(target = "subjectId", source = "subject.id")
    PostDto toDto(Post post);

    @Mapping(target = "author.id",    source = "userId")
    @Mapping(target = "subject.id",   source = "subjectId")
    Post toEntity(PostDto dto);
}
