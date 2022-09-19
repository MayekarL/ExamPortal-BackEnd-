package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.pojo.CategoryDto;
import com.exam.pojo.CategoryResponse;
import com.exam.service.CategoryService;

@CrossOrigin("*")
@RequestMapping("/category")
@RestController
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("/create")
	public ResponseEntity<CategoryResponse> addCategory(@RequestBody CategoryDto categoryDto) {
		System.out.println("CategoryDto : " + categoryDto);
		CategoryResponse categoryResponse = categoryService.addCategory(categoryDto);
		return new ResponseEntity<CategoryResponse>(categoryResponse, categoryResponse.getStatus());
	}
 
	@PutMapping("/update/{id}")
	public ResponseEntity<CategoryResponse> updateCategory(@PathVariable("id") Long id,
			@RequestBody CategoryDto categoryDto) {
		System.out.println("CategoryDto : " + categoryDto);
		CategoryResponse categoryResponse = categoryService.updateCategory(id, categoryDto);
		return new ResponseEntity<CategoryResponse>(categoryResponse, categoryResponse.getStatus());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("id") Long id) {
		System.out.println("ID : " + id);
		CategoryResponse categoryResponse = categoryService.getCategory(id);
		return new ResponseEntity<CategoryResponse>(categoryResponse, categoryResponse.getStatus());
	}
	
	@GetMapping("/get")
	public ResponseEntity<CategoryResponse> getAllCategories() {
		CategoryResponse categoryResponse = categoryService.getAllCategories();
		return new ResponseEntity<CategoryResponse>(categoryResponse, categoryResponse.getStatus());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<CategoryResponse> deleteCategoryById(@PathVariable("id") Long id) {
		System.out.println("ID : " + id);
		CategoryResponse categoryResponse = categoryService.deleteCategory(id);
		return new ResponseEntity<CategoryResponse>(categoryResponse, categoryResponse.getStatus());
	}

}
