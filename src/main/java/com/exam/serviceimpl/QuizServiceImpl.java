package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.exam.constants.ExamConstants;
import com.exam.entity.Quiz;
import com.exam.pojo.QuizDto;
import com.exam.pojo.QuizResponse;
import com.exam.repo.QuizRepo;
import com.exam.service.QuizService;

@Service
public class QuizServiceImpl implements QuizService{

	@Autowired
	private QuizRepo quizRepo;
	
	@Autowired
	private ModelMapper model;
	
	@Override
	public QuizResponse addQuiz(QuizDto quizDto) {
		QuizResponse quizResponse = new QuizResponse();
		try {
			Quiz quiz = model.map(quizDto, Quiz.class);
			Quiz saved = quizRepo.save(quiz);
			quizResponse.setCode(0);
			quizResponse.setMessage(ExamConstants.SUCCESS);
			quizResponse.setStatus(HttpStatus.OK);
			quizResponse.setQuizDto(model.map(saved, QuizDto.class));
		} catch (Exception ex) {
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			quizResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return quizResponse;
	}

	@Override
	public QuizResponse updateQuiz(Long id, QuizDto quizDto) {
		QuizResponse quizResponse = new QuizResponse();
		try {
			if (id == quizDto.getqId()) {
				Quiz newQuiz = quizRepo.findById(id).get();
				newQuiz.setqId(quizDto.getqId());
				newQuiz.setDescription(quizDto.getDescription());
				newQuiz.setTitle(quizDto.getTitle());
				newQuiz.setMaxMarks(quizDto.getMaxMarks());
				newQuiz.setNumberOfQuestions(quizDto.getNumberOfQuestions());
				newQuiz.setActive(quizDto.isActive());
				newQuiz.setCategory(quizDto.getCategory());
				newQuiz.setQuestions(quizDto.getQuestions());
				Quiz updatedQuiz = quizRepo.save(newQuiz);
				quizResponse.setCode(0);
				quizResponse.setMessage(ExamConstants.SUCCESS);
				quizResponse.setStatus(HttpStatus.OK);
				quizResponse.setQuizDto(model.map(updatedQuiz, QuizDto.class));
			} else {
				quizResponse.setCode(1);
				quizResponse.setMessage(ExamConstants.ID_DOESNT_MATCH);
				quizResponse.setStatus(HttpStatus.BAD_REQUEST);
			}
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.NO_QUIZ_FOUND);
			quizResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			quizResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return quizResponse;
	}

	@Override
	public QuizResponse getQuiz(Long id) {
		QuizResponse quizResponse = new QuizResponse();
		try {
			Quiz quiz = quizRepo.findById(id).get();
			quizResponse.setCode(0);
			quizResponse.setMessage(ExamConstants.SUCCESS);
			quizResponse.setStatus(HttpStatus.OK);
			quizResponse.setQuizDto(model.map(quiz, QuizDto.class));
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.NO_QUIZ_FOUND);
			quizResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			quizResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return quizResponse;
	}

	@Override
	public QuizResponse getAllQuizes() {
		QuizResponse quizResponse = new QuizResponse();
		List<Quiz> quizList = quizRepo.findAll();
		List<QuizDto> quizDtoList = new ArrayList<>(); 
		if (!quizList.isEmpty()) {
			quizResponse.setCode(0);
			quizResponse.setMessage(ExamConstants.SUCCESS);
			quizResponse.setStatus(HttpStatus.OK);
			
			for(Quiz q : quizList) {
				quizDtoList.add(model.map(q, QuizDto.class));
			}
			quizResponse.setQuizDtos(quizDtoList);
		} else {
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			quizResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return quizResponse;
		
	}

	@Override
	public QuizResponse deleteQuiz(Long id) {
		QuizResponse quizResponse = new QuizResponse();
		try {
			Quiz quiz = quizRepo.findById(id).get();
			quizRepo.delete(quiz);
			quizResponse.setCode(0);
			quizResponse.setMessage(ExamConstants.SUCCESS);
			quizResponse.setStatus(HttpStatus.OK);
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			quizResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			quizResponse.setCode(1);
			quizResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			quizResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return quizResponse;
	}
	
}
