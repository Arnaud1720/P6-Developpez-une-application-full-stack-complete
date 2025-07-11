package com.openclassrooms.mddapi.dto.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubjectDataDto {
    @NotBlank(message = "Le nom du sujet est obligatoire")
    private String name;

    @Size(max = 255)
    private String description;
}
