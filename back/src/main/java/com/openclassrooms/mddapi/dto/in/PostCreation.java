package com.openclassrooms.mddapi.dto.in;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreation {

    @NotBlank(message = "Le titre est obligatoire")
    private String title;

    @NotBlank(message = "Le contenu est obligatoire")
    private String content;

    /**
     * ID du sujet : si fourni et existe → on utilise.
     * Si absent ou non trouvé, on passera par `subjectData` pour en créer un.
     */
    private Integer subjectId;

    /**
     * Données du sujet à créer si `subjectId` vaut null ou introuvable.
     * Peut contenir uniquement le nom (et éventuellement description).
     */
    @Valid
    private SubjectDataDto subjectData;
}
