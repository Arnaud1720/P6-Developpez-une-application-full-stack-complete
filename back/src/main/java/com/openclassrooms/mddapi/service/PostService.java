package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.dto.in.PostCreation;

public interface PostService {
    PostDto create(PostCreation dto, Integer userId);
    PostDto getPostBySubjetIdAndPostId(Integer postId, Integer subjectId);
}
