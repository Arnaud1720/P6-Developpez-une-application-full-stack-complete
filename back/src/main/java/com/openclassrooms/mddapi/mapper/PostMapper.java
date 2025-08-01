package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(target = "userId", source = "userid.id")
    PostDto toDto(Post post);

    @Mapping(target = "userid.id",    source = "userId")
    Post toEntity(PostDto dto);
}
