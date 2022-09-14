package com.exam.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtil;
import com.exam.config.UserDetailServiceImpl;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.pojo.JwtResponse;
import com.exam.pojo.Jwtrequest;
import com.exam.pojo.UserDto;
import com.exam.pojo.UserRequest;


@CrossOrigin("*")
@RestController
public class AuthenticateController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailServiceImpl userDetailServiceImpl;

	@Autowired
	private ModelMapper modelMapper;

	private void authenticate(String username, String password) throws Exception {

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER IS DISABLED");
		} catch (BadCredentialsException e) {
			throw new Exception("USER IS DISABLED");
		}
	}

	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody Jwtrequest jwtrequest) throws Exception {
		try {

			authenticate(jwtrequest.getUsername(), jwtrequest.getPassword());

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User Name Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(jwtrequest.getUsername());
		String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new JwtResponse(token));
	}

	@GetMapping("/current-user")
	public UserDto getCurrentUser(Principal principal) {
		UserDto userDto =new UserDto();
		User user = (User) userDetailServiceImpl.loadUserByUsername(principal.getName());
		List<String>roles = new ArrayList<String>();
		for(UserRole ur : user.getUserRoles()) {
			roles.add(ur.getRole().getRoleName());
		}
		userDto.setRoleName(roles);
		userDto.setUserRequest(modelMapper.map(user, UserRequest.class));
		return userDto;
	}
}
