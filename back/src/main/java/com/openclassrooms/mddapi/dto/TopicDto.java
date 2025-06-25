package com.openclassrooms.mddapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDto {
    private Integer idTopic;
    private String name;
    private String description;
    private int idSubject;
    private int    userId;     // ← juste l’ID, pas besoin de User user

}
