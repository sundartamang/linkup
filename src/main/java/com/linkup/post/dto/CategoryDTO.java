package com.linkup.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {
    private Integer id;
    @NotBlank(message = "title can not be blank")
    private String title;
    @Size( max = 100, message = "description must be max of 100 characters !")
    private String description;
}
