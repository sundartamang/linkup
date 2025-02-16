package com.linkup.post.payload;

import com.linkup.post.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostResponse {
    private List<PostDTO> data;
    private int pageNumber;
    private int pageSize;
    private long totalCount;
    private int totalPage;
    private boolean isLastPage;
}
