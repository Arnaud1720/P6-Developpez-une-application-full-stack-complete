package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Setter(AccessLevel.NONE)
    @Schema(
            description = "Identifiant du sujet (readonly)",
            example     = "2",
            accessMode  = Schema.AccessMode.READ_ONLY
    )
    private Integer id_subject;

    @Schema(
            description = "Nom du sujet",
            example     = "Technologie"
    )
    private String name;

    @Schema(
            description = "Date de création du sujet",
            example     = "2025-07-02T15:31:32.889"
    )
    private LocalDateTime creationDate;

    @Schema(
            description = "Description détaillée du sujet",
            example     = "Discussions autour des dernières innovations technologiques"
    )
    private String description;

}
