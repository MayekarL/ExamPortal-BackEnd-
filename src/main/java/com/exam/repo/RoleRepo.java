package com.exam.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.Role;

@Repository
public interface RoleRepo extends JpaRepository<Role, Long>{

}
