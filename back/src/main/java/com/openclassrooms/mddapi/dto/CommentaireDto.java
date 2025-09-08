package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireDto {
    private Integer id;
    @NotBlank(message = "Le commentaire ne peut pas être vide")
    private String contenu;
    private LocalDateTime createdAt;

    private Integer postId;
    private Integer auteurId;
    private String auteurUsername;
}
