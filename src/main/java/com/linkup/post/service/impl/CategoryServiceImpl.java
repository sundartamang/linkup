package com.linkup.post.service.impl;

import com.linkup.exceptions.ResourceNotFoundException;
import com.linkup.post.dto.CategoryDTO;
import com.linkup.post.entity.Category;
import com.linkup.post.repository.CategoryRepo;
import com.linkup.post.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    public CategoryServiceImpl(CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = this.modelMapper.map(categoryDTO, Category.class);
        Category savedCategory = this.categoryRepo.save(category);
        logger.info("{} : category created ", categoryDTO.getTitle());
        return this.modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", " id", categoryId));
        category.setTitle(categoryDTO.getTitle());
        category.setDescription(categoryDTO.getDescription());
        Category updatedCategory = this.categoryRepo.save(category);
        logger.info("{} : category updated ", categoryDTO.getId());
        return this.modelMapper.map(updatedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryDetails(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", " id", categoryId));
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = this.categoryRepo.findAll();
        return categories.stream().map(
                (category)-> this.modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(
                ()-> new ResourceNotFoundException("Category", " id", categoryId));
        logger.info("Category with: {} deleted successfully", categoryId);
        this.categoryRepo.delete(category);
    }
}
