package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.exam.constants.ExamConstants;
import com.exam.entity.Category;
import com.exam.pojo.CategoryDto;
import com.exam.pojo.CategoryResponse;
import com.exam.repo.CategoryRepo;
import com.exam.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper model;

	@Override
	public CategoryResponse addCategory(CategoryDto categoryDto) {
		CategoryResponse categoryResponse = new CategoryResponse();
		try {
			Category category = model.map(categoryDto, Category.class);
			Category saved = categoryRepo.save(category);
			categoryResponse.setCode(0);
			categoryResponse.setMessage(ExamConstants.SUCCESS);
			categoryResponse.setStatus(HttpStatus.OK);
			categoryResponse.setCategory(model.map(saved, CategoryDto.class));
		} catch (Exception ex) {
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			categoryResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}

		return categoryResponse;
	}

	@Override
	public CategoryResponse updateCategory(Long id, CategoryDto categoryDto) {
		CategoryResponse categoryResponse = new CategoryResponse();
		try {
			if (id == categoryDto.getcId()) {
				Category newCategory = categoryRepo.findById(id).get();

				newCategory.setcId(categoryDto.getcId());
				newCategory.setDescription(categoryDto.getDescription());
				newCategory.setTitle(categoryDto.getTitle());
				newCategory.setQuizzes(categoryDto.getQuizzes());

				Category updatedCategory = categoryRepo.save(newCategory);
				categoryResponse.setCode(0);
				categoryResponse.setMessage(ExamConstants.SUCCESS);
				categoryResponse.setStatus(HttpStatus.OK);
				categoryResponse.setCategory(model.map(updatedCategory, CategoryDto.class));
			} else {
				categoryResponse.setCode(1);
				categoryResponse.setMessage(ExamConstants.ID_DOESNT_MATCH);
				categoryResponse.setStatus(HttpStatus.BAD_REQUEST);
			}

		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			categoryResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			categoryResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}

		return categoryResponse;
	}

	@Override
	public CategoryResponse getCategory(Long id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		try {
			Category category = categoryRepo.findById(id).get();

			categoryResponse.setCode(0);
			categoryResponse.setMessage(ExamConstants.SUCCESS);
			categoryResponse.setStatus(HttpStatus.OK);
			categoryResponse.setCategory(model.map(category, CategoryDto.class));

		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			categoryResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			categoryResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return categoryResponse;
	}

	@Override
	public CategoryResponse getAllCategories() {
		CategoryResponse categoryResponse = new CategoryResponse();
		List<Category> categories = categoryRepo.findAll();
		List<CategoryDto> categoriesList = new ArrayList<>();

		if (!categories.isEmpty()) {
			categoryResponse.setCode(0);
			categoryResponse.setMessage(ExamConstants.SUCCESS);
			categoryResponse.setStatus(HttpStatus.OK);
			for (Category c : categories) {
				categoriesList.add(model.map(c, CategoryDto.class));
			}
			categoryResponse.setCategoryList(categoriesList);
		} else {
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			categoryResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return categoryResponse;
	}

	@Override
	public CategoryResponse deleteCategory(Long id) {
		CategoryResponse categoryResponse = new CategoryResponse();
		try {
			Category category = categoryRepo.findById(id).get();
			categoryRepo.delete(category);
			categoryResponse.setCode(0);
			categoryResponse.setMessage(ExamConstants.SUCCESS);
			categoryResponse.setStatus(HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while Deleting user : " + ex);
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			categoryResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			categoryResponse.setCode(1);
			categoryResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			categoryResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return categoryResponse;
	}

}
