package com.exam.service;

import com.exam.pojo.QuizDto;
import com.exam.pojo.QuizResponse;

public interface QuizService {

	QuizResponse addQuiz(QuizDto quizDto);

	QuizResponse updateQuiz(QuizDto quizDto);

	QuizResponse getQuiz(Long id);

	QuizResponse getAllQuizes();

	QuizResponse deleteQuiz(Long id);

	QuizResponse getQuizByCategory(Long id);

	QuizResponse getActiveQuiz(boolean b);

	QuizResponse getActiveQuizofCategory(Long id, boolean b);
}
