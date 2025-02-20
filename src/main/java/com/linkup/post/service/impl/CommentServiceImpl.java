package com.linkup.post.service.impl;

import com.linkup.exceptions.ResourceNotFoundException;
import com.linkup.post.dto.CommentDTO;
import com.linkup.post.entity.Comment;
import com.linkup.post.repository.CommentRepo;
import com.linkup.post.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepo commentRepo, ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = this.modelMapper.map(commentDTO, Comment.class);
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment = this.commentRepo.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment", "id", commentId));
        this.commentRepo.delete(comment);
    }
}
