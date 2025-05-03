package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;



public interface CategoryService {
    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortOrder, String sortBy);
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long categoryId);

}
