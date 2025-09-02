package com.openclassrooms.mddapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ThemeDto {
    @Schema(
            description = "Identifiant de l'abonnement",
            example     = "1",
            accessMode  = Schema.AccessMode.READ_ONLY
    )
    private int id;
    @Schema(
            description = "Titre du theme",
            example     = "titre",
            accessMode  = Schema.AccessMode.AUTO
    )
    private String title;
    @Schema(
            description = "contenu du theme",
            example = "contenue du theme",
            accessMode  = Schema.AccessMode.AUTO
    )
    private String content;
}
