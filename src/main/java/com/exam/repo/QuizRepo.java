package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.Category;
import com.exam.entity.Quiz;

@Repository
public interface QuizRepo extends JpaRepository<Quiz, Long>{

	List<Quiz> findByCategory(Category category);
	List<Quiz> findByActive(boolean b);
	List<Quiz> findByCategoryAndActive(Category c,boolean b);

		
}
