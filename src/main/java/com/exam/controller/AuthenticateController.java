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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.config.JwtUtil;
import com.exam.config.UserDetailServiceImpl;
import com.exam.constants.ExamConstants;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.pojo.JwtResponse;
import com.exam.pojo.Jwtrequest;
import com.exam.pojo.Response;
import com.exam.pojo.UserDto;
import com.exam.pojo.UserRequest;
import org.springframework.security.core.Authentication;

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

	@Autowired
	BCryptPasswordEncoder encoder;

	private void authenticate(String username, String password) throws Exception {

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			System.out.println("Authentication : " + authentication);
		} catch (DisabledException e) {
			throw new Exception("USER IS DISABLED");
		} catch (BadCredentialsException e) {
			throw new Exception("USER IS DISABLED");
		} catch (Exception e) {
			throw new Exception(" ");
		}
	}

	@PostMapping("/generate-token")
	public ResponseEntity<?> generateToken(@RequestBody Jwtrequest jwtrequest) throws Exception {
		try {
			System.out.println("Jwt Request : " + jwtrequest);
			authenticate(jwtrequest.getUsername(), jwtrequest.getPassword());

		} catch (UsernameNotFoundException e) {
			e.printStackTrace();
			throw new Exception("User Name Not Found");
		} catch (Exception e) {
			e.printStackTrace();
		}
		UserDetails userDetails = userDetailServiceImpl.loadUserByUsername(jwtrequest.getUsername());
		System.out.println("User Details (Authenticate Service): " + userDetails);
		String token = jwtUtil.generateToken(userDetails);
		System.out.println("generated Token : " + token);
		System.out.println(userDetails.getPassword().equals(encoder.encode(jwtrequest.getPassword())));
		System.out.println(userDetails.getPassword());
		System.out.println(encoder.encode(jwtrequest.getPassword()));
		return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
//		if (userDetails.getPassword().equals(encoder.encode(jwtrequest.getPassword()))) {
//			return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
//		} else {
//			return new ResponseEntity<>(new Response(1, ExamConstants.WRONG_PASSWORD), HttpStatus.BAD_REQUEST);
//		}

	}

	@GetMapping("/current-user")
	public UserDto getCurrentUser(Principal principal) {
		UserDto userDto = new UserDto();
		User user = (User) userDetailServiceImpl.loadUserByUsername(principal.getName());
		List<String> roles = new ArrayList<String>();
		for (UserRole ur : user.getUserRoles()) {
			roles.add(ur.getRole().getRoleName());
		}
		userDto.setRoleName(roles);
		userDto.setUserRequest(modelMapper.map(user, UserRequest.class));
		return userDto;
	}
}
