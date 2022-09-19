package com.exam.pojo;

import java.util.LinkedHashSet;
import java.util.Set;

import com.exam.entity.Quiz;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CategoryDto {

	private Long cId;
	private String title;
	private String description;
	@JsonIgnore
	private Set<Quiz> quizzes = new LinkedHashSet<>();

	public Long getcId() {
		return cId;
	}

	public void setcId(Long cId) {
		this.cId = cId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(Set<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	@Override
	public String toString() {
		return "CategoryDto [cId=" + cId + ", title=" + title + ", description=" + description + ", quizzes=" + quizzes
				+ "]";
	}

}
