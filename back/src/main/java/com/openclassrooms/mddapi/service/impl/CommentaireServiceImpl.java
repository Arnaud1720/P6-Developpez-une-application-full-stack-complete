package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.CommentaireDto;
import com.openclassrooms.mddapi.mapper.CommentaireMapper;
import com.openclassrooms.mddapi.repository.CommentaireRepository;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.CommentaireService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.Post;
import com.openclassrooms.mddapi.entity.User;
import org.springframework.web.server.ResponseStatusException;
import com.openclassrooms.mddapi.entity.Commentaire;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class CommentaireServiceImpl  implements CommentaireService {
    private final CommentaireRepository repo;
    private final PostRepository postRepo;
    private final UserRepository userRepo;
    private final CommentaireMapper mapper; // MapStruct ou manuel

    public CommentaireServiceImpl(CommentaireRepository repo, PostRepository postRepo, UserRepository userRepo, CommentaireMapper mapper) {
        this.repo = repo;
        this.postRepo = postRepo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }
    @Override
    public CommentaireDto createComment(Integer postId, Integer userId, String contenu) {
        LocalDateTime date = LocalDateTime.now();
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post introuvable"));

        User auteur = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Commentaire com = Commentaire.builder()
                .contenu(contenu)
                .post(post)
                .createdAt(date)
                .auteur(auteur)
                .build();

        return mapper.toDto(repo.save(com));
    }
    @Override
    public List<CommentaireDto> listCommentsByPost(Integer postId) {
        return repo.findAllByPostIdOrderByCreatedAtDesc(postId)
                .stream()
                .map(mapper::toDto)
                .toList();
    }
}
