package com.openclassrooms.mddapi.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "nom")
    private String firstname;
    @Column(name = "prenom")
    private String lastname;
    @Column(name = "email")
    private String email;
    private String postalAdress;
    @Column(name = "password")
    private String password;
    private LocalDateTime createdAt  = LocalDateTime.now();
    private LocalDateTime updateAt = LocalDateTime.now();
    @Column(name = "username",nullable = true)
    private String username;
}
