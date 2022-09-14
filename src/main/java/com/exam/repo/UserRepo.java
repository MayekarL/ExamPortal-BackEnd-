package com.exam.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long>{

	public User findByEmail(String email);
	
}
