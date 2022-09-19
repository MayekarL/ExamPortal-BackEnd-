package com.exam.pojo;

import java.util.List;

import org.springframework.http.HttpStatus;

public class QuizResponse {

	private Integer code;
	private String message;
	private QuizDto QuizDto;
	private List<QuizDto> quizDtos;
	private HttpStatus status;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public QuizDto getQuizDto() {
		return QuizDto;
	}

	public void setQuizDto(QuizDto quizDto) {
		QuizDto = quizDto;
	}

	public List<QuizDto> getQuizDtos() {
		return quizDtos;
	}

	public void setQuizDtos(List<QuizDto> quizDtos) {
		this.quizDtos = quizDtos;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "QuizResponse [code=" + code + ", message=" + message + ", QuizDto=" + QuizDto + ", quizDtos=" + quizDtos
				+ ", status=" + status + "]";
	}

}
