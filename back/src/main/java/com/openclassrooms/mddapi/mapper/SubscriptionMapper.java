package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SubscriptionMapper {


    // entity -> dto
    @Mapping(target = "userId",  source = "user.id")
    @Mapping(target = "themeId", source = "theme.id")
    @Mapping(target = "themeName", source = "theme.title")
    @Mapping(target = "themeDescription", source = "theme.content")
    SubscriptionDto toDto(Subscription subscription);

    @Mapping(target = "user",  ignore = true)
    @Mapping(target = "theme", ignore = true)
    Subscription toEntity(SubscriptionDto dto);

}