package com.openclassrooms.mddapi.service;
import com.openclassrooms.mddapi.dto.CommentaireDto;

import java.util.List;

public interface CommentaireService {
     CommentaireDto createComment(Integer postId, Integer userId, String contenu);
     List<CommentaireDto> listCommentsByPost(Integer postId);
}
