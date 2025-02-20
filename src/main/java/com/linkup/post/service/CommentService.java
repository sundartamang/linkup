package com.linkup.post.service;

import com.linkup.post.dto.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO);
    void deleteComment(Integer commentId);
}
