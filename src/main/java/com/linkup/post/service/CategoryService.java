package com.linkup.post.service;

import com.linkup.post.dto.CategoryDTO;
import java.util.List;

public interface CategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId);
    CategoryDTO getCategoryDetails(Integer categoryId);
    List<CategoryDTO> getCategories();
    void deleteCategory(Integer categoryId);
}
