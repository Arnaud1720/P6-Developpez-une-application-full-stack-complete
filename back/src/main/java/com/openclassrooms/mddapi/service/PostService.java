package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;

import java.util.List;

public interface PostService {
    PostDto create(PostCreation dto, Integer userId);
    PostDto getPostBySubjetIdAndPostId(Integer postId, Integer subjectId);
    List<PostDto> findAll();
    List<PostDto> getPostsOrderByDate(boolean asc);
}
