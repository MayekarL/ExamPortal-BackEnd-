package com.exam.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class QuestionResponse {

	private int code;
	private String message;
	private QuestionDto questionDto;
	private List<QuestionDto> QuestionList = new ArrayList<QuestionDto>();
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

	public QuestionDto getQuestionDto() {
		return questionDto;
	}

	public void setQuestionDto(QuestionDto questionDto) {
		this.questionDto = questionDto;
	}

	public List<QuestionDto> getQuestionList() {
		return QuestionList;
	}

	public void setQuestionList(List<QuestionDto> questionList) {
		QuestionList = questionList;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "QuestionResponse [code=" + code + ", message=" + message + ", questionDto=" + questionDto
				+ ", QuestionList=" + QuestionList + ", status=" + status + "]";
	}

}
