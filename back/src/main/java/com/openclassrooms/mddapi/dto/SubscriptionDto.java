package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {


    @Schema(description = "Identifiant de l'abonnement", example = "1",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Integer id;

    @Schema(description = "ID de l'utilisateur abonné", example = "1")
    private Integer userId;

    @Schema(description = "ID du thème concerné", example = "42")
    private Integer themeId;

    @Schema(description = "Horodatage de l'abonnement")
    private LocalDateTime subscribedAt;

    @Schema(description = "Horodatage du désabonnement (null si toujours actif)")
    private LocalDateTime unsubscribedAt;
}
