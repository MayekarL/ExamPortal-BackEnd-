package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.exam.constants.ExamConstants;
import com.exam.entity.Question;
import com.exam.entity.Quiz;
import com.exam.pojo.QuestionDto;
import com.exam.pojo.QuestionResponse;
import com.exam.pojo.QuizResponse;
import com.exam.repo.QuestionRepo;
import com.exam.repo.QuizRepo;
import com.exam.service.QuestionService;
import com.exam.service.QuizService;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	private QuestionRepo questionRepo;

	@Autowired
	private QuizService quizService;

	@Autowired
	private ModelMapper model;

	@Override
	public QuestionResponse createQuestion(QuestionDto questionDto) {
		QuestionResponse questionResponse = new QuestionResponse();
		try {
			Question question = model.map(questionDto, Question.class);
			Question saved = questionRepo.save(question);
			questionResponse.setCode(0);
			questionResponse.setMessage(ExamConstants.SUCCESS);
			questionResponse.setStatus(HttpStatus.OK);
			questionResponse.setQuestionDto(model.map(saved, QuestionDto.class));
		} catch (Exception ex) {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return questionResponse;
	}

	@Override
	public QuestionResponse updateQuestion(Long qId, QuestionDto questionDto) {
		QuestionResponse questionResponse = new QuestionResponse();
		try {
			if (qId == questionDto.getId()) {
				Question question = questionRepo.findById(qId).get();
				question.setId(questionDto.getId());
				question.setOption1(questionDto.getOption1());
				question.setOption2(questionDto.getOption2());
				question.setOption3(questionDto.getOption3());
				question.setOption4(questionDto.getOption4());
				question.setQuiz(questionDto.getQuiz());
				question.setAnswer(questionDto.getAnswer());
				question.setContent(questionDto.getContent());
				Question updatedQuestion = questionRepo.save(question);
				questionResponse.setCode(0);
				questionResponse.setMessage(ExamConstants.SUCCESS);
				questionResponse.setStatus(HttpStatus.OK);
				questionResponse.setQuestionDto(model.map(updatedQuestion, QuestionDto.class));
			} else {
				questionResponse.setCode(1);
				questionResponse.setMessage(ExamConstants.ID_DOESNT_MATCH);
				questionResponse.setStatus(HttpStatus.BAD_REQUEST);
			}
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			questionResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}

		return questionResponse;
	}

	@Override
	public QuestionResponse getQuestion(Long qId) {
		QuestionResponse questionResponse = new QuestionResponse();
		try {
			Question question = questionRepo.findById(qId).get();

			questionResponse.setCode(0);
			questionResponse.setMessage(ExamConstants.SUCCESS);
			questionResponse.setStatus(HttpStatus.OK);
			questionResponse.setQuestionDto(model.map(question, QuestionDto.class));
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			questionResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return questionResponse;

	}

	@Override
	public QuestionResponse deleteQuestion(Long qId) {
		QuestionResponse questionResponse = new QuestionResponse();
		try {
			Question question = questionRepo.findById(qId).get();
			questionRepo.delete(question);
			questionResponse.setCode(0);
			questionResponse.setMessage(ExamConstants.SUCCESS);
			questionResponse.setStatus(HttpStatus.OK);
			questionResponse.setQuestionDto(model.map(question, QuestionDto.class));
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while UPDATING user : " + ex);
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.NO_CATEGORY_FOUND);
			questionResponse.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return questionResponse;
	}

	@Override
	public QuestionResponse getAllQuestions() {
		QuestionResponse questionResponse = new QuestionResponse();
		List<Question> questions = questionRepo.findAll();
		List<QuestionDto> questionList = new ArrayList<>();

		if (!questions.isEmpty()) {
			questionResponse.setCode(0);
			questionResponse.setMessage(ExamConstants.SUCCESS);
			questionResponse.setStatus(HttpStatus.OK);
			for (Question c : questions) {
				questionList.add(model.map(c, QuestionDto.class));
			}
			questionResponse.setQuestionList(questionList);
		} else {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return questionResponse;
	}

	@Override
	public QuestionResponse getQuestionsOfQuiz(Long quizId) {
		QuestionResponse questionResponse = new QuestionResponse();
		try {
			QuizResponse quizResponse = quizService.getQuiz(quizId);
			Set<Question> questions = quizResponse.getQuizDto().getQuestions();
			List list = new ArrayList(questions);
			if (list.size() > quizResponse.getQuizDto().getNumberOfQuestions()) {
				list = list.subList(0, quizResponse.getQuizDto().getNumberOfQuestions() + 1);
			}
			questionResponse.setCode(0);
			questionResponse.setMessage(ExamConstants.SUCCESS);
			questionResponse.setStatus(HttpStatus.OK);
			Collections.shuffle(list);
			questionResponse.setQuestionList(list);

		} catch (Exception ex) {
			questionResponse.setCode(1);
			questionResponse.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			questionResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			System.out.println("Exception : " + ex);
		}
		return questionResponse;
	}

	@Override
	public Map<String, Integer> calculateMarks(List<QuestionDto> questionList) {
		Map<String, Integer> marks = new HashMap<String, Integer>();
		try {
			Double marksGot = 0.0;
			Integer correctAnswers = 0;
			Integer attempted = 0;
			Integer  marksPerQuestion = (int) (questionList.get(0).getQuiz().getMaxMarks() / questionList.size());
			for (QuestionDto question : questionList) {
				
				if (question.getGivenAnswer().equalsIgnoreCase(question.getAnswer())) {
					correctAnswers++;
				}
				
				if(!question.getGivenAnswer().equalsIgnoreCase("")) {
					attempted++;
				}
				
			}
			marksGot = (double) (correctAnswers * marksPerQuestion);
			Integer totalMarks = (int) Math.round(marksGot);
			marks.put("marksGot", totalMarks);
			marks.put("correctAnswers", correctAnswers);
			marks.put("marksPerQuestion", marksPerQuestion);
			marks.put("attempted", attempted);
		} catch (Exception ex) {

			System.out.println("Exception while calculating marks : " + ex);
		}

		return marks;
	}

}
