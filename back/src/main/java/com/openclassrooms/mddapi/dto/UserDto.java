package com.openclassrooms.mddapi.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Integer id;
    @NotBlank
    @Size(min = 2, max = 20, message = "votre prenom doit contenir au moins 2 caractère minimun et 20 caractère maximum")
    private String firstname;
    @Size(min = 2, max = 20, message = "votre nom doit contenir au moins 2 caractère minimun et 20 caractère maximum")
    private String lastname;
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Le format de l'email est invalide")
    private String email;
    @NotBlank(message = "L'adresse postale est obligatoire")
    @Size(max = 255, message = "L'adresse postale est trop longue")
    private String postalAdress;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Pattern(
            regexp = "^(?=.*[#?!|_&]).{6,}$",
            message = "Le mot de passe doit comporter au moins 6 caractères et inclure au moins un caractère spécial parmi # ? ! | _ &"
    )
    private String password;
    @PastOrPresent(message = "La date de création doit être dans le passé ou aujourd'hui")
    private LocalDateTime createdAt;
    @PastOrPresent(message = "La date de mise à jour doit être dans le passé ou aujourd'hui")
    private LocalDateTime updatedAt;

}
