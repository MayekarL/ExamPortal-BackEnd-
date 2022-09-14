package com.exam.entity.service;

import com.exam.pojo.UserDto;
import com.exam.pojo.UserRequest;

public interface UserService {

	public UserDto createUser(UserRequest userRequest);
	
	public UserDto getUserByEmailId(String emailId);
	
	public UserDto getUserByUserId(String userId);
	
	public UserDto deleteUserById(Long id);

	public UserDto updateUserByEmailId(UserRequest userRequest,Long id);
}
