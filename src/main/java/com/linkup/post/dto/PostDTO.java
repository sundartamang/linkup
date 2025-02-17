package com.linkup.post.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class PostDTO {
    private Integer id;
    @NotBlank(message = "Title can not be blank")
    @Size(min = 3, max = 100, message = "Title must be min of 3 and max of 100 characters !")
    private String title;
    @NotBlank(message = "Content can not be blank")
    private String content;
    private String imageName;
    private Date addedDate;
    private Integer userId;
    private Integer categoryId;

}
