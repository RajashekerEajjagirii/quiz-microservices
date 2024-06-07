package com.raj.quiz_service.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.raj.quiz_service.Fiegn.QuizInterface;
import com.raj.quiz_service.Models.Question;
import com.raj.quiz_service.Models.QuestionWrapper;
import com.raj.quiz_service.Models.Quiz;
import com.raj.quiz_service.Models.Response;
import com.raj.quiz_service.Repository.QuizRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
	
	@Autowired
	QuizRepo quizRepo;
	
	@Autowired
	QuizInterface quizInterface;

	public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
		// TODO Auto-generated method stub
		//calling generate Quiz using Feign
		List<Integer> questions = quizInterface.getQuestionsForQuiz(category, numQ).getBody();
		Quiz quiz = new Quiz();
		quiz.setTitle(title);
		quiz.setQuestionIds(questions);
		quizRepo.save(quiz);
		return new ResponseEntity<>("Quiz was Created",HttpStatus.CREATED);
	}

	//Fetching Quiz Questions	
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
		// TODO Auto-generated method stub
		Quiz quiz= quizRepo.findById(id).get();
		List<Integer> questionIds = quiz.getQuestionIds();
		ResponseEntity<List<QuestionWrapper>> questions = quizInterface.getQuestionsFromId(questionIds);
		return questions;

	}

	// Calculate Quiz Result
	public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
		// TODO Auto-generated method stub
		
		ResponseEntity<Integer> score = quizInterface.getScore(responses);
		// No need to print in ResponseEntity format because we already doing in question-service
		return score;
	}
	
	
}
