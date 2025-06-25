package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.entity.Topic;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDto toDto(Topic topic);
    Topic toEntity(TopicDto topicDto);

}
