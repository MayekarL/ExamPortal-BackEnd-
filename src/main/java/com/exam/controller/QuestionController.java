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

import com.exam.pojo.QuestionDto;
import com.exam.pojo.QuestionResponse;
import com.exam.pojo.QuizResponse;
import com.exam.service.QuestionService;

@RestController
@CrossOrigin("*")
@RequestMapping("/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	
	@PostMapping("/create")
	public ResponseEntity<QuestionResponse> createQuestion(@RequestBody QuestionDto questionDto) {
		QuestionResponse questionResponse = questionService.createQuestion(questionDto);
		return new ResponseEntity<QuestionResponse>(questionResponse,questionResponse.getStatus());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<QuestionResponse> getQuestionById(@PathVariable("id") Long id){
		QuestionResponse questionResponse = questionService.getQuestionsOfQuiz(id);
		return new ResponseEntity<QuestionResponse>(questionResponse,questionResponse.getStatus()); 
	}
	
	@PutMapping("/update/{id}")
	public ResponseEntity<QuestionResponse> updateQuestion(@PathVariable("id") Long id,@RequestBody QuestionDto questionDto){
		QuestionResponse questionResponse = questionService.updateQuestion(id,questionDto);
		return new ResponseEntity<QuestionResponse>(questionResponse,questionResponse.getStatus()); 
	}
	
	@GetMapping("/")
	public ResponseEntity<QuestionResponse> getQuestions(){
		QuestionResponse questionResponse = questionService.getAllQuestions();
		return new ResponseEntity<QuestionResponse>(questionResponse,questionResponse.getStatus()); 
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<QuestionResponse> deleteQuestionById(@PathVariable("id") Long id){
		QuestionResponse questionResponse = questionService.deleteQuestion(id);
		return new ResponseEntity<QuestionResponse>(questionResponse,questionResponse.getStatus()); 
	}
}
