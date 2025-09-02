package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Theme {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
@Column(nullable = false)
private String title;

@Column(columnDefinition = "TEXT", nullable = false)
private String content;
}
