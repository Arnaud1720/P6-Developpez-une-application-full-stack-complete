package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileDto {
    // Pas de validation ici car l'ID est forcé côté controller
    private Integer id;
    @Email(message = "L'email doit être valide")
    private String email;
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    // Champs optionnels sans validation @NotBlank
    private String username;
    private String firstname;
    private String lastname;
}
