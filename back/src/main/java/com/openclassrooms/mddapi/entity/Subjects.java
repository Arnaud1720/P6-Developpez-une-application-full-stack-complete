package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subjects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_subject", unique = true)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(length = 255)
    private String description;

    @Column
    private LocalDateTime creationDate = LocalDateTime.now();
    @Column
    private LocalDateTime modifiedDate;
    // Optionnel : liste des posts de ce sujet
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy="subjects")
    private List<Subscription> subscriptions;
}
