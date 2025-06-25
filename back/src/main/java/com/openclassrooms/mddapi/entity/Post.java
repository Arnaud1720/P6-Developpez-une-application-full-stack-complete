package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    @ManyToOne
    private User author;
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
