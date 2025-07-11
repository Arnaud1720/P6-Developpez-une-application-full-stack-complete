package com.openclassrooms.mddapi.service.impl;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.SubjectRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.entity.Subjects;
import com.openclassrooms.mddapi.entity.Post;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import javax.security.auth.Subject;
import java.time.LocalDateTime;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    public PostServiceImpl(PostRepository postRepository, SubjectRepository subjectRepository, UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto create(PostCreation dto, Integer userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        Subjects subject;
        if (dto.getSubjectId() != null) {
            // on essaye d’abord de récupérer l’ID s’il a été donné
            subject = subjectRepository.findById(dto.getSubjectId())
                    .orElse(null);
        } else {
            subject = null;
        }

        if (subject == null) {
            // soit l’ID n’a pas été fourni, soit il est invalide → on crée un nouveau sujet
            if (dto.getSubjectData() == null) {
                throw new IllegalArgumentException(
                        "Aucun subjectId valide ni données de sujet fournies"
                );
            }
            subject = Subjects.builder()
                    .name(dto.getSubjectData().getName())
                    .description(dto.getSubjectData().getDescription())
                    .build();
            subject = subjectRepository.save(subject);
        }

        Post post = Post.builder()
                .title(dto.getTitle())
                .createdAt(LocalDateTime.now())
                .content(dto.getContent())
                .author(author)
                .subject(subject)
                .build();

        Post saved = postRepository.save(post);
        return postMapper.toDto(saved);
    }

    @Override
    public PostDto getPostBySubjetIdAndPostId(Integer postId, Integer subjectId) {
        Post post = postRepository
                .findByIdAndSubjectId(postId, subjectId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Aucun article #" + postId + " pour le sujet #" + subjectId
                        )
                );
        return postMapper.toDto(post);
    }
}
