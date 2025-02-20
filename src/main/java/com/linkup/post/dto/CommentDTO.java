package com.linkup.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Integer id;

    @NotBlank(message = "Comment cannot be blank")
    @Size(max = 100, message = "Comment cannot exceed 100 characters!")
    private String comment;
    private Integer postId;
}

