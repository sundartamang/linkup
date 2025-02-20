package com.linkup.post.controller;

import com.linkup.auth.payload.ApiResponse;
import com.linkup.post.dto.CommentDTO;
import com.linkup.post.service.CommentService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/comment/")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO) {
        CommentDTO comments = this.commentService.createComment(commentDTO);
        logger.info("Comment created successfully with ID: {}", comments.getId());
        return new ResponseEntity<>(comments, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId) {
        this.commentService.deleteComment(commentId);
        logger.info("Comment with ID {} deleted successfully", commentId);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully.", true));
    }
}
