package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.exam.constants.ExamConstants;
import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.entity.UserRole;
import com.exam.pojo.UserDto;
import com.exam.pojo.UserRequest;
import com.exam.repo.RoleRepo;
import com.exam.repo.UserRepo;
import com.exam.service.UserService;


@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private RoleRepo roleRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public UserDto createUser(UserRequest userRequest) {
		UserDto userDto = new UserDto();
		try {
			userRequest.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
			User localUser = userRepo.findByEmail(userRequest.getEmail());
			if (localUser != null) {
				System.out.println("The User is already Present ");
				userDto.setCode(1);
				userDto.setMessage(ExamConstants.USER_EXISTS);
				userDto.setStatus(HttpStatus.BAD_REQUEST);
				return userDto;
			} else {
				Role role = new Role();
				role.setRoleId(ExamConstants.NORMAL_CODE);
				role.setRoleName(ExamConstants.NORMAL);
				Role savedRole = roleRepo.save(role);

				Set<UserRole> userRoles = new HashSet<>();
				UserRole userRole = new UserRole();
				userRole.setRole(savedRole);
				if (userRequest.getProfile() == null) {
					userRequest.setProfile(ExamConstants.PROFILE_PIC_URL);
				}
				User user = modelMapper.map(userRequest, User.class);
				userRole.setUser(user);
				userRoles.add(userRole);

				user.getUserRoles().add(userRole);
				localUser = userRepo.save(user);
				
				userDto.setCode(0);
				userDto.setMessage(ExamConstants.SUCCESS);
				userDto.setStatus(HttpStatus.OK);
				List<String> roles = new ArrayList<>();
				for(UserRole ur :user.getUserRoles())
				{
					roles.add(ur.getRole().getRoleName());
				}
				
				userDto.setRoleName(roles);
				userDto.setUserList(Arrays.asList(modelMapper.map(localUser, UserRequest.class)));
				return userDto;
			}
		} catch (Exception ex) {
			System.out.println("Exception while creating user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			userDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return userDto;
		}

	}

	@Override
	public UserDto getUserByEmailId(String emailId) {

		UserDto userDto = new UserDto();
		try {
			User user = userRepo.findByEmail(emailId);
			if (user == null) {
				userDto.setCode(1);
				userDto.setMessage(ExamConstants.NO_USER_FOUND);
				userDto.setStatus(HttpStatus.BAD_REQUEST);
			} else {
				userDto.setCode(0);
				userDto.setMessage(ExamConstants.SUCCESS);
				userDto.setStatus(HttpStatus.OK);
				userDto.setUserRequest(modelMapper.map(user, UserRequest.class));
			}
		} catch (Exception ex) {
			System.out.println("Exception while fetching user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			userDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return userDto;
	}

	@Override
	public UserDto deleteUserById(Long id) {
		UserDto userDto = new UserDto();
		try {
			User user = userRepo.findById(id).get();
			if (user != null) {
				userRepo.delete(user);
				userDto.setCode(0);
				userDto.setMessage(ExamConstants.SUCCESS);
				userDto.setStatus(HttpStatus.OK);
			}
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while deleting user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.NO_USER_FOUND);
			userDto.setStatus(HttpStatus.BAD_REQUEST);
		}catch (Exception ex) {
			System.out.println("Exception while deleting user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			userDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return userDto;
	}

	@Override
	public UserDto getUserByUserId(String userId) {

		return null;
	}

	@Override
	public UserDto updateUserByEmailId(UserRequest userRequest, Long id) {
		UserDto userDto = new UserDto();
		try {
			User localUser = userRepo.findById(id).get();
			if (localUser != null) {
				if (localUser.getId() == userRequest.getId()) {
					User user = new User();
					user.setId(userRequest.getId());
					user.setFirstName(userRequest.getFirstName());
					user.setAbout(userRequest.getAbout());
					user.setContactNumber(userRequest.getContactNumber());
					user.setEnabled(userRequest.isEnabled());
					user.setLastName(userRequest.getLastName());
					user.setPassword(userRequest.getPassword());
					user.setProfile(userRequest.getProfile());
					User updatedUser = userRepo.save(user);
					userDto.setCode(0);
					userDto.setMessage(ExamConstants.SUCCESS);
					userDto.setStatus(HttpStatus.OK);
					userDto.setUserRequest(modelMapper.map(updatedUser, UserRequest.class));
				} else {
					userDto.setCode(1);
					userDto.setMessage("User "+ExamConstants.ID_DOESNT_MATCH);
					userDto.setStatus(HttpStatus.BAD_REQUEST);
				}

			}
		} catch (NoSuchElementException ex) {
			System.out.println("Exception while deleting user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.NO_USER_FOUND);
			userDto.setStatus(HttpStatus.BAD_REQUEST);
		} catch (Exception ex) {
			System.out.println("Exception while deleting user : " + ex);
			userDto.setCode(1);
			userDto.setMessage(ExamConstants.SOMETHING_WENT_WRONG);
			userDto.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return userDto;
	}

}
