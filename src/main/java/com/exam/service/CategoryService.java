package com.exam.service;

import com.exam.pojo.CategoryDto;
import com.exam.pojo.CategoryResponse;

public interface CategoryService {

	CategoryResponse addCategory(CategoryDto categoryDto);
	CategoryResponse updateCategory(Long id,CategoryDto categoryDto);
	CategoryResponse getCategory(Long id);
	CategoryResponse getAllCategories();
	CategoryResponse deleteCategory(Long id);
	
}
