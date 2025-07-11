package com.openclassrooms.mddapi.utils;

import com.fasterxml.jackson.annotation.JsonAlias;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @Schema(description="Username ou email", example="arnaud1720@gmail.com")
    @JsonAlias({ "usernameOrEmail", "username", "email" })
    private String usernameOrEmail;

    @Schema(description="Mot de passe", example="••••••")
    private String password;
}
