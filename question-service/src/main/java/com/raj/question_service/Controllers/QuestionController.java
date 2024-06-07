package com.raj.question_service.Controllers;

import com.raj.question_service.Models.Question;
import com.raj.question_service.Models.QuestionWrapper;
import com.raj.question_service.Models.Response;
import com.raj.question_service.Repository.QuestionRepo;
import com.raj.question_service.Services.QuestionService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("question")
public class QuestionController {
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	QuestionRepo questionRepo;
	
	@Autowired
	Environment environment;
	
	@GetMapping("/allQuestions")
	public ResponseEntity<List<Question>> getAllQuestions()
	{
		List<Question> aq=null;
		try {
			aq=questionService.getAllQuestions();
			return ResponseEntity.status(HttpStatus.OK).body(aq);
		}catch(Exception e) 
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	@GetMapping("category/{topic}")
	public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String topic)
	{
		List<Question> cl=null;
		try {
			cl=questionService. getQuestionsByCategory(topic);
			return ResponseEntity.status(HttpStatus.OK).body(cl);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}
	
	//adding Question	
	@PostMapping("/addQuestion")
	public ResponseEntity<Question> addQuestion(@RequestBody Question question)
	{
		Question aq=null;
		try {
			aq=questionService.addQuestion(question );
			return ResponseEntity.status(HttpStatus.CREATED).body(aq);
		}
		catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); 
		}
	}
	
	//updating Question	
	@PutMapping("updateQuestion/{id}")
	public ResponseEntity<Question> updateQuestion(@RequestBody Question question, @PathVariable Integer id)
	{
		Question uq=null;
		try {
			uq=questionService.updateQuestion(question,id);
			return ResponseEntity.status(HttpStatus.OK).body(uq);
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//			return new ResponseEntity<>(body,HttpStatus.BAD_REQUEST); 
		}
	}
	
	
	//	Deleting Question
	@DeleteMapping("delete/{id}")
	public ResponseEntity<Question> deleteQuestion(@PathVariable Integer id)
	{		
		Optional<Question> getque=questionRepo.findById(id);
		if(getque.isPresent()) {
			 questionRepo.deleteById(id);
			 return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); 
		}else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		  // return new ResponseEntity<>(body:"User not found",HttpStatus.NOT_FOUND);//for String
		}
		
	}
	
	
	//Generating Questions
	@GetMapping("generate")
	public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numOfQues)
	{
		return questionService.getQuestionsForQuiz(category,numOfQues);
	}
	
	
	//Get Questions for Quiz
	@PostMapping("getQuestions")
	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds)
	{
		System.out.println(environment.getProperty("local.server.port"));
		return questionService.getQuestionsFromId(questionIds);
	}
	
	
	//Calculate Quiz Score
	@PostMapping("getScore")
	public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses)
	{
		return questionService.getScore(responses);
	}
	
}
