package com.raj.quiz_service.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.raj.quiz_service.Models.QuestionWrapper;
import com.raj.quiz_service.Models.QuizDto;
import com.raj.quiz_service.Models.Response;
import com.raj.quiz_service.Services.QuizService;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
	
	@Autowired
	QuizService quizService;
	
	@PostMapping("create")
	public ResponseEntity<String> createQuiz(@RequestBody QuizDto quizDto)
	{
		return quizService.createQuiz(quizDto.getCategoryName(),quizDto.getNumQuestions(),quizDto.getTitle());
		//	return new ResponseEntity<>("I am Here",HttpStatus.CREATED);   
	}
	
	
	//Getting Quiz Questions
	@PostMapping("get/{id}")
	public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id)
	{

		return quizService.getQuizQuestions(id);
	}
	
	
	//Submit Quiz and provide results
	@PostMapping("submit/{id}")
	public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses)
	{
		return quizService.calculateResult(id,responses);
	}
}
