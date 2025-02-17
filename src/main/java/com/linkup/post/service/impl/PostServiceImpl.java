package com.linkup.post.service.impl;

import com.linkup.auth.entity.Users;
import com.linkup.auth.repository.UserRepo;
import com.linkup.exceptions.ResourceNotFoundException;
import com.linkup.post.dto.PostDTO;
import com.linkup.post.entity.Category;
import com.linkup.post.entity.Post;
import com.linkup.post.payload.PostRequest;
import com.linkup.post.payload.PostResponse;
import com.linkup.post.repository.CategoryRepo;
import com.linkup.post.repository.PostRepo;
import com.linkup.post.service.PostService;
import com.linkup.utils.AppConstant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepo postRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO) {
        Users user = this.userRepo.findById(postDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User", " id", postDTO.getUserId()));
        Category category = this.categoryRepo.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", " id", postDTO.getCategoryId()));
        Post post = this.modelMapper.map(postDTO, Post.class);
        post.setUsers(user);
        post.setCategory(category);
        if(postDTO.getImageName() != null){
            post.setImageName(postDTO.getImageName());
        }else{
            post.setImageName("default.png");
        }
        post.setAddedDate(new Date());
        Post newPost = this.postRepo.save(post);
        return this.modelMapper.map(newPost, PostDTO.class);
    }

    @Override
    public PostDTO updatePost(PostDTO postDTO, Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id", postId));
        Category category = this.categoryRepo.findById(postDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", " id", postDTO.getCategoryId()));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());
        post.setCategory(category);
        Post updatedPost = this.postRepo.save(post);
        return this.modelMapper.map(updatedPost, PostDTO.class);
    }

    @Override
    public PostDTO getPostById(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id", postId));
        return this.modelMapper.map(post, PostDTO.class);
    }

    @Override
    public PostResponse getPosts(PostRequest postRequest) {
        String sortDir = postRequest.getSortDir();
        Sort sort = sortDir.equalsIgnoreCase(AppConstant.SORT_DIR)
                ? Sort.by(sortDir).ascending() : Sort.by(sortDir).descending();
        Pageable pageable = PageRequest.of(
                postRequest.getPageNumber(), postRequest.getPageSize(), sort);
        Page<Post> pageRequest = this.postRepo.findAll(pageable);
        List<PostDTO> postDTOS = pageRequest.getContent()
                .stream().map((post)-> this.modelMapper.map(post, PostDTO.class))
                .collect(Collectors.toList());
        return new PostResponse(
                postDTOS,
                pageRequest.getNumber(),
                pageRequest.getSize(),
                pageRequest.getTotalElements(),
                pageRequest.getTotalPages(),
                pageRequest.isLast()
        );
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = this.postRepo.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post", " id", postId));
        this.postRepo.delete(post);
    }

    @Override
    public List<PostDTO> searchPosts(String keyword) {
        List<Post> filteredPost = this.postRepo.findByTitleContaining(keyword);
        return filteredPost.stream().map(
                (post) -> this.modelMapper.map(post, PostDTO.class)).collect(Collectors.toList());
    }
}
