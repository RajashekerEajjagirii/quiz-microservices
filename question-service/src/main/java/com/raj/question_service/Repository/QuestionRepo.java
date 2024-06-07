package com.raj.question_service.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raj.question_service.Models.Question;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question, Integer> {

	//fetching data based on category column
	List<Question> findByCategory(String category);

	//Creating Quiz based on category and no of questions
	@Query(value="SELECT q.id FROM question q where q.category=:category ORDER BY rand() limit :numQ", nativeQuery=true)
	public List<Integer> findRandomQuestionsByCategory(@Param("category") String category,@Param("numQ") int numQ); 

}
