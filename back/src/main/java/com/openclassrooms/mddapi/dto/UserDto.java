package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {

    @Schema(description = "ID généré par le serveur", accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @Schema(description = "Prénom de l'utilisateur", example = "Alice")
//    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 20, message = "Votre prénom doit contenir entre 2 et 20 caractères")
    private String firstname;

    @Schema(description = "Nom de l'utilisateur", example = "Dupont")
    @Size(min = 2, max = 20, message = "Votre nom doit contenir entre 2 et 20 caractères")
    private String lastname;

    @Schema(description = "Adresse e-mail", example = "alice@example.com")
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    private String email;

    @Schema(description = "Adresse postale", example = "123 rue de Paris")
//    @NotBlank(message = "L'adresse postale est obligatoire")
    @Size(max = 255, message = "L'adresse postale est trop longue")
    private String postalAdress;

    @Schema(description = "Mot de passe (uniquement en écriture)", accessMode = Schema.AccessMode.WRITE_ONLY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Pattern(
            regexp = "^(?=.*[#?!|_&]).{6,}$",
            message = "Le mot de passe doit comporter au moins 6 caractères et inclure au moins un caractère spécial parmi # ? ! | _ &"
    )
    private String password;

    @Schema(description = "Nom d'utilisateur pour la connexion", example = "alice123")
    @NotBlank(message = "Le nom d'utilisateur est obligatoire")
    private String username;

    @Schema(description = "Date de création", example = "2025-06-30T10:22:33.608555",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @PastOrPresent(message = "La date de création doit être dans le passé ou aujourd'hui")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière mise à jour", example = "2025-06-30T10:22:33.608555",
            accessMode = Schema.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @PastOrPresent(message = "La date de mise à jour doit être dans le passé ou aujourd'hui")
    private LocalDateTime updatedAt;

}
