package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private int userId;
    private int subjectId;
    private String authorPseudo;
    private String subjectName;
    private String topicName;
}
