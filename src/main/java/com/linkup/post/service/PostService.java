package com.linkup.post.service;

import com.linkup.post.dto.PostDTO;
import com.linkup.post.payload.PostRequest;
import com.linkup.post.payload.PostResponse;
import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);
    PostDTO updatePost(PostDTO postDTO, Integer postId);
    PostDTO getPostById(Integer postId);
    PostResponse getPosts(PostRequest postRequest);
    void deletePost(Integer postId);
    List<PostDTO> searchPosts(String keyword);
}
