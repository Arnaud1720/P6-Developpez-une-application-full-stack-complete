package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfilDto {
    @Schema(
            description = "Identifiant unique du profil",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Prénom de l'utilisateur",
            example = "Arnaud"
    )
    private String firstname;

    @Schema(
            description = "Nom de famille de l'utilisateur",
            example = "Derisbourg"
    )
    private String lastname;

    @Schema(
            description = "Adresse e-mail de l'utilisateur",
            example = "arnaud.derisbourg@example.com"
    )
    private String email;

    @Schema(
            description = "Nombre total de sujets créés par l'utilisateur",
            example = "5",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long topicsCreatedCount;

    @Schema(
            description = "Nombre total d'abonnements",
            example = "3",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long subscriptionsCount;

    @Schema(
            description = "Nombre de sujets auxquels l'utilisateur est abonné",
            example = "2",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private long subscribedSubjectsCount;

    @Schema(
            description = "Liste détaillée des abonnements",
            example = "[{\"id\":1,\"userId\":1,\"subjectId\":2,\"subscribedAt\":\"2025-07-01T12:34:56Z\",\"unsubscribedAt\":null}]"
    )
    private List<SubscriptionDto> subscriptions;


}
