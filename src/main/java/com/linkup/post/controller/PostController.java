package com.linkup.post.controller;

import com.linkup.auth.payload.ApiResponse;
import com.linkup.post.dto.PostDTO;
import com.linkup.post.payload.PostRequest;
import com.linkup.post.payload.PostResponse;
import com.linkup.post.service.FileService;
import com.linkup.post.service.PostService;
import com.linkup.utils.AppConstant;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    private final PostService postService;
    private final FileService fileService;

    public PostController(PostService postService, FileService fileService) {
        this.postService = postService;
        this.fileService = fileService;
    }

    @PostMapping("/")
    public ResponseEntity<PostDTO> createPost(
            @Valid @ModelAttribute PostDTO postDTO,
            @RequestPart(value = "image", required = false)MultipartFile image) throws IOException {
        String fileName = this.fileService.uploadImage(AppConstant.POST_FILE_PATH, image);
        postDTO.setImageName(fileName);
        PostDTO post = this.postService.createPost(postDTO);
        return new ResponseEntity<PostDTO>(post, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDTO> updatePost(
            @Valid @ModelAttribute PostDTO postDTO,
            @PathVariable Integer postId,
            @RequestPart(value = "image", required = false)MultipartFile image) throws IOException{
        PostDTO existingPost = this.postService.getPostById(postId);
        // delete old image if exists
        if(existingPost.getImageName() != null){
            this.fileService.deleteFile(AppConstant.POST_FILE_PATH, existingPost.getImageName());
        }
        // upload new image
        String fileName = this.fileService.uploadImage(AppConstant.POST_FILE_PATH, image);
        postDTO.setImageName(fileName);

        PostDTO post = this.postService.updatePost(postDTO, postId);
        return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getPostDetails(@PathVariable Integer postId){
        PostDTO post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDTO>(post, HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<PostResponse> getPosts(@Valid @RequestBody PostRequest postRequest){
        PostResponse postResponse = this.postService.getPosts(postRequest);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) throws IOException{
        PostDTO postDTO = this.postService.getPostById(postId);
        //delete the image associated with post
        if(postDTO.getImageName() != null){
            this.fileService.deleteFile(AppConstant.POST_FILE_PATH, postDTO.getImageName());
        }
        this.postService.deletePost(postId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Post delete successfully", true), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchPost(@PathVariable String keyword){
        List<PostDTO> postDTOS = this.postService.searchPosts(keyword);
        return new ResponseEntity<List<PostDTO>>(postDTOS, HttpStatus.OK);
    }

}
