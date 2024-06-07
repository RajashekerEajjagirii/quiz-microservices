package com.raj.quiz_service.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raj.quiz_service.Models.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Integer> {

}
