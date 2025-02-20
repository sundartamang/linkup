package com.linkup.post.controller;

import com.linkup.auth.payload.ApiResponse;
import com.linkup.post.dto.CommentDTO;
import com.linkup.post.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post/comment/")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{postId}")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO){
        CommentDTO comments = this.commentService.createComment(commentDTO);
        return new ResponseEntity<CommentDTO>(comments, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        this.commentService.deleteComment(commentId);
        return ResponseEntity.ok(new ApiResponse("Comment deleted successfully.", true));
    }
}
