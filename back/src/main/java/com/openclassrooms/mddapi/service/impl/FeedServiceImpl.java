package com.openclassrooms.mddapi.service.impl;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.service.FeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.openclassrooms.mddapi.entity.Post;
import java.util.List;

@Service
@Slf4j
public class FeedServiceImpl implements FeedService {
    private final PostRepository postRepo;
    private final PostMapper postMapper;

    public FeedServiceImpl(PostRepository postRepo, PostMapper postMapper) {
        this.postRepo = postRepo;
        this.postMapper = postMapper;
    }


    @Override
    public List<PostDto> getFeed(Integer userId, boolean asc) {
        Sort sort = Sort.by("createdAt");
        sort = asc ? sort.ascending() : sort.descending();
        return postRepo.findFeedByUser(userId, sort)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }
}
