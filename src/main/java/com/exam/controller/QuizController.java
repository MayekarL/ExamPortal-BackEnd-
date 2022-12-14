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

import com.exam.pojo.QuizDto;
import com.exam.pojo.QuizResponse;
import com.exam.service.QuizService;

@CrossOrigin("*")
@RequestMapping("/quiz")
@RestController
public class QuizController {

	@Autowired
	private QuizService quizService;
	
	@PostMapping("/create")
	public ResponseEntity<QuizResponse> createQuiz(@RequestBody QuizDto quizDto) {
		QuizResponse quizResponse = quizService.addQuiz(quizDto);
		return new ResponseEntity<QuizResponse>(quizResponse,quizResponse.getStatus());
	}

	@PutMapping("/update")
	public ResponseEntity<QuizResponse> updateQuiz(
			@RequestBody QuizDto quizDto) {
		System.out.println("Quiz Dto : " + quizDto);
		QuizResponse quizResponse = quizService.updateQuiz(quizDto);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<QuizResponse> getQuizById(@PathVariable("id") Long id) {
		System.out.println("ID : " + id);
		QuizResponse quizResponse = quizService.getQuiz(id);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}
	
	@GetMapping("/get")
	public ResponseEntity<QuizResponse> getAllQuizes() {
		QuizResponse quizResponse = quizService.getAllQuizes();
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<QuizResponse> deleteQuizById(@PathVariable("id") Long id) {
		System.out.println("ID : " + id);
		QuizResponse quizResponse = quizService.deleteQuiz(id);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}

	@GetMapping("/catgeories/{cId}")
	public ResponseEntity<QuizResponse>getQuizByCategory(@PathVariable("cId") Long cId){
		System.out.println("ID : " + cId);
		QuizResponse quizResponse = quizService.getQuizByCategory(cId);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}
	
	@GetMapping("/get/active")
	public ResponseEntity<QuizResponse> getActiveQuizes() {
		QuizResponse quizResponse = quizService.getActiveQuiz(true);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}
	
	@GetMapping("/active/catgeories/{cId}")
	public ResponseEntity<QuizResponse>getActiveQuizByCategory(@PathVariable("cId") Long cId){
		System.out.println("ID : " + cId);
		QuizResponse quizResponse = quizService.getActiveQuizofCategory(cId,true);
		return new ResponseEntity<QuizResponse>(quizResponse, quizResponse.getStatus());
	}
	
	
	
}
