package com.linkup.post.repository;
import com.linkup.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByTitleContaining(String keyword);
}
