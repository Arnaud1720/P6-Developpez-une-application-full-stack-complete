package com.openclassrooms.mddapi.service.impl;
import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.repository.ThemeRepository;
import com.openclassrooms.mddapi.repository.UserRepository;
import com.openclassrooms.mddapi.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.User;
import com.openclassrooms.mddapi.entity.Post;
import org.springframework.web.server.ResponseStatusException;
import com.openclassrooms.mddapi.entity.Theme;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final ThemeRepository themeRepository;
    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, PostMapper postMapper, ThemeRepository themeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
        this.themeRepository = themeRepository;
    }

    @Override
    public PostDto create(PostCreation dto, Integer userId) {
        User author = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur introuvable"));

        // AJOUT: Gestion du thème
        Theme theme = null;
        if (dto.getSubjectId() != null) {
            theme = themeRepository.findById(dto.getSubjectId()).orElse(null);
        }

        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .createdAt(LocalDateTime.now())
                .userid(author)
                .theme(theme) // AJOUT
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
    @Override
    public PostDto findById(Integer id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post introuvable"));
        return postMapper.toDto(post);
    }
}



