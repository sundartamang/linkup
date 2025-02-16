package com.linkup.post.payload;

import com.linkup.post.dto.PostDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostRequest {
    private Integer pageNumber;
    private Integer PageSize;
    private String sortBy;
    private String sortDir;
}