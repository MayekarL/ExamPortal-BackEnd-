package com.exam.service;

import java.util.List;
import java.util.Map;

import com.exam.pojo.QuestionDto;
import com.exam.pojo.QuestionResponse;

public interface QuestionService {

	QuestionResponse createQuestion(QuestionDto questionDto);
	QuestionResponse updateQuestion(Long qId,QuestionDto questionDto);
	QuestionResponse getQuestion(Long qId);
	QuestionResponse deleteQuestion(Long qId);
	QuestionResponse getAllQuestions();
	QuestionResponse getQuestionsOfQuiz(Long quizId);
	Map<String,Integer> calculateMarks(List<QuestionDto> questionList); 
	
}
