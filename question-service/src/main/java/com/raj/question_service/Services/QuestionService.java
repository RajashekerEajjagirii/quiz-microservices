package com.raj.question_service.Services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.raj.question_service.Models.Question;
import com.raj.question_service.Models.QuestionWrapper;
import com.raj.question_service.Models.Response;
import com.raj.question_service.Repository.QuestionRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

	@Autowired
	QuestionRepo questionRepo;
	
	public List<Question> getAllQuestions() {
		// TODO Auto-generated method stub
		return questionRepo.findAll();
	}

	public List<Question> getQuestionsByCategory(String topic) {
		// TODO Auto-generated method stub
		return questionRepo.findByCategory(topic);
	}

	public Question addQuestion(Question question) {
		// TODO Auto-generated method stub
		return questionRepo.save(question);
	}

	public Question updateQuestion(Question question, Integer id) {
		// TODO Auto-generated method stub
		Question uq=null;
		Optional<Question> getque=questionRepo.findById(id);
		if(getque.isPresent()) {
			uq = getque.get();
			uq.setCategory(question.getCategory());
			uq.setDifficultyLevel(question.getDifficultyLevel());
			uq.setOption1(question.getOption1());
			uq.setOption2(question.getOption2());
			uq.setOption3(question.getOption3());
			uq.setOption4(question.getOption4());
			uq.setQuestionTitle(question.getQuestionTitle());
			uq.setRightAnswer(question.getRightAnswer());
			return questionRepo.save(uq);
		}
		throw new RuntimeException("User is not found for id: "+id);
	}

	public Question deleteQuestion(Integer id) {
		// TODO Auto-generated method stub
		Optional<Question> getque=questionRepo.findById(id);
		if(getque.isPresent()) {
			 questionRepo.deleteById(id);
		}
		throw new RuntimeException("User was not found for id: "+id);
	}

	public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numOfQues) {
		// TODO Auto-generated method stub
		//Getting Questions id's
		List<Integer> questions= questionRepo.findRandomQuestionsByCategory(category,numOfQues);
		return new ResponseEntity<>(questions, HttpStatus.OK);
	}

	public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionIds) 
	{
		// TODO Auto-generated method stub
		
		 List<QuestionWrapper> wrappers= new ArrayList<>();
		 List<Question> questions = new ArrayList<>();
		 
		 //Based on id getting questions and add to the question
		 for(Integer id: questionIds) {
			 questions.add(questionRepo.findById(id).get());
		 }
		 
		 for(Question que: questions) {
			 QuestionWrapper wrapper= new QuestionWrapper();
			 wrapper.setId(que.getId());
			 wrapper.setQuestionTitle(que.getQuestionTitle());
			 wrapper.setOption1(que.getOption1());
			 wrapper.setOption2(que.getOption2());
			 wrapper.setOption3(que.getOption3());
			 wrapper.setOption4(que.getOption4());
			 wrappers.add(wrapper);
			 
		 }		 
		 return new ResponseEntity<>(wrappers, HttpStatus.OK);
	}

	public ResponseEntity<Integer> getScore(List<Response> responses) {
		// TODO Auto-generated method stub
		
		int right=0;
		for(Response res: responses) {
			Question question = questionRepo.findById(res.getId()).get();
			if(res.getResponse().equals(question.getRightAnswer()))
					right++;
		}
		return new ResponseEntity<>(right, HttpStatus.OK);
	}

}
