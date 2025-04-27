package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class CategoryServiceImpl implements CategoryService{
    //private final List<Category> categories = new ArrayList<>();
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    //long nextId = 1L;

    @Override
    public Category updateCategory(Category category, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
//        Category savedCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!"));
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return  savedCategory;
        //        List<Category> categories = categoryRepository.findAll();
        //        Optional<Category> optionalCategory = categories.stream()
        //                .filter(c-> c.getCategoryId().equals(categoryId))
        //                .findFirst();
        //        if(optionalCategory.isPresent()){
        //            Category existingCategory = optionalCategory.get();
        //            existingCategory.setCategoryName(category.getCategoryName());
        //            return categoryRepository.save(existingCategory);
        //        }else{
        //            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!");
        //        }
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(savedCategory != null){
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!!");
        }
        //category.setCategoryId(nextId++);
        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category deleteCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category","categoryId",categoryId));
        //        List<Category> categories = categoryRepository.findAll();
        //        Category category = categories.stream()
        //                .filter(c-> c.getCategoryId().equals(categoryId))
        //                .findFirst()
        //                .orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND,"Resource not found!"));
        categoryRepository.delete(deleteCategory);

        return "Category with categoryId " + categoryId + " deleted successfully!!";
    }
}
