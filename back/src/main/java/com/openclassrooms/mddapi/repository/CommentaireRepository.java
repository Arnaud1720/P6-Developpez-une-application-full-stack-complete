package com.openclassrooms.mddapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.openclassrooms.mddapi.entity.Commentaire;

import java.util.Arrays;
import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire,Integer> {

    List<Commentaire> findAllByPostIdOrderByCreatedAtDesc(Integer postId);
}
