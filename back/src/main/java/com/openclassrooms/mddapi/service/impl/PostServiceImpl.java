package com.openclassrooms.mddapi.service.impl;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.entity.Post;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public PostServiceImpl(PostRepository postRepository,UserRepository userRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    @Override
    public PostDto create(PostCreation dto, Integer userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));
//TODO décom peut-être

//        Subjects subject = null;
//        if (dto.getSubjectId() != null) {
//            subject = subjectRepository.findById(dto.getSubjectId()).orElse(null);
//        } else if (dto.getSubjectData() != null) {
//            // créer un nouveau sujet
//            subject = Subjects.builder()
//                    .name(dto.getSubjectData().getName())
//                    .description(dto.getSubjectData().getDescription())
//                    .build();
//            subject = subjectRepository.save(subject);
//        }
        // ici subject peut rester null

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .userid(author)
//                .subject(subject)   // null autorisé
                .build();

        return postMapper.toDto(postRepository.save(post));
    }



    @Override
    public List<PostDto> findAll() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(postMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<PostDto> getPostsOrderByDate(boolean asc) {
        List<Post> posts = asc
                ? postRepository.findAllByOrderByCreatedAtAsc()
                : postRepository.findAllByOrderByCreatedAtDesc();
        return posts.stream()
                .map(postMapper::toDto)
                .collect(Collectors.toList());
    }
}



