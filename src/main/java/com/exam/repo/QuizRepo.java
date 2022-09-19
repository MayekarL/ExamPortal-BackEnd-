package com.exam.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.Question;
import com.exam.entity.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long>{

	
	
}
