package com.raj.quiz_service.Fiegn;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.raj.quiz_service.Models.QuestionWrapper;
import com.raj.quiz_service.Models.Response;



@FeignClient("QUESTION-SERVICE")
public interface QuizInterface {
	
	//Generating Questions
		@GetMapping("question/generate")
		public ResponseEntity<List<Integer>> getQuestionsForQuiz(@RequestParam String category, @RequestParam Integer numOfQues);


		//Get Questions for Quiz
		@PostMapping("question/getQuestions")
		public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(@RequestBody List<Integer> questionIds);

		//Calculate Quiz Score
		@PostMapping("question/getScore")
		public ResponseEntity<Integer> getScore(@RequestBody List<Response> responses);
	
}
