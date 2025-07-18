package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer id;
    @Schema(description="titre", example="titreTest")
    private String title;
    @Schema(description="titre", example="titreTest")
    private String content;
    private LocalDateTime createdAt;
    private int userId;
    @Schema(description="SubjectUser", example="1",nullable = true)
    private int subjectId;

}
