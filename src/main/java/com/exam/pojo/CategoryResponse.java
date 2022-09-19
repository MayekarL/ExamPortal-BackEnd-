package com.exam.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CategoryResponse {

	private int code;
	private String message;
	private CategoryDto category;
	private List<CategoryDto> categoryList = new ArrayList<CategoryDto>();
	private HttpStatus status;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public CategoryDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDto category) {
		this.category = category;
	}

	public List<CategoryDto> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<CategoryDto> categoryList) {
		this.categoryList = categoryList;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "CatgeoryResponse [code=" + code + ", message=" + message + ", category=" + category + ", categoryList="
				+ categoryList + ", status=" + status + "]";
	}

}
