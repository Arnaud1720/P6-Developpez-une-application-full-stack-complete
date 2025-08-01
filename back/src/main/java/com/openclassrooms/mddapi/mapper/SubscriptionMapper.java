package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.SubscriptionDto;
import com.openclassrooms.mddapi.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface SubscriptionMapper {

    // Mapping entity -> DTO (récupère l'id du post)
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "userId", source = "user.id")
    SubscriptionDto toDto(Subscription subscription);

    // Mapping DTO -> entity (tu peux laisser comme ça pour l’instant, voir plus bas)
    Subscription toEntity(SubscriptionDto subscriptionDto);
}