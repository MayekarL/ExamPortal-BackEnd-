package com.exam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.pojo.UserDto;
import com.exam.pojo.UserRequest;
import com.exam.service.UserService;

@CrossOrigin("*")
@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	//Create New User 
	@PostMapping("/user/create")
	public ResponseEntity<UserDto> createUser(@RequestBody UserRequest userRequest){
		System.out.println("Insidde create User : "+userRequest);
		UserDto userDto= userService.createUser(userRequest);
		return new ResponseEntity<UserDto>(userDto,userDto.getStatus());
	}
	
	//Fetch By UserName 
	@GetMapping("/admin/{username}")
	public ResponseEntity<UserDto> getUserByEmailId(@PathVariable("username") String emailId){
		System.out.println("Insidde get User By Email Id: "+emailId);
		UserDto userDto= userService.getUserByEmailId(emailId);
		return new ResponseEntity<UserDto>(userDto,userDto.getStatus());	
	}
	
	//Delete By Id 
	@DeleteMapping("/admin/delete/{id}")
	public ResponseEntity<UserDto> deleteUserById(@PathVariable("id") Long id){
		System.out.println("Insidde delete User By Email Id: "+id);
		UserDto userDto= userService.deleteUserById(id);
		return new ResponseEntity<UserDto>(userDto,userDto.getStatus());	
	}
	

	//Update 
	@PutMapping("/user/update/{id}")
	public ResponseEntity<UserDto> updateUserByEmailId(@RequestBody UserRequest userRequest,@PathVariable("id") Long id){
		System.out.println("Insidde update User By Email Id: "+userRequest);
		UserDto userDto= userService.updateUserByEmailId(userRequest,id);
		return new ResponseEntity<UserDto>(userDto,userDto.getStatus());
	}
}
